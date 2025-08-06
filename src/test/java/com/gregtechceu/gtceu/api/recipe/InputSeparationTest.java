package com.gregtechceu.gtceu.api.recipe;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import com.gregtechceu.gtceu.gametest.util.TestUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.LARGE_CHEMICAL_RECIPES;

@PrefixGameTestTemplate(false)
@GameTestHolder(GTCEu.MOD_ID)
public class InputSeparationTest {

    @BeforeBatch(batch = "InputSeparation")
    public static void prepare(ServerLevel level) {
        // Force insert the recipe into the manager.
        LARGE_CHEMICAL_RECIPES.getLookup().removeAllRecipes();
        LARGE_CHEMICAL_RECIPES.getLookup().addRecipe(LARGE_CHEMICAL_RECIPES
                .recipeBuilder(GTCEu.id("test-multiblock-input-separation"))
                .id(GTCEu.id("test-multiblock-input-separation"))
                .inputItems(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.ACACIA_WOOD))
                .outputItems(new ItemStack(Blocks.STONE))
                .EUt(GTValues.VA[GTValues.HV]).duration(1)
                // NBT has a schematic in it with an HV energy input hatch
                .buildRawRecipe());
    }

    private static MetaMachine getMetaMachine(BlockEntity entity) {
        return ((MetaMachineBlockEntity) entity).getMetaMachine();
    }

    private record BusHolder(ItemBusPartMachine inputBus1, ItemBusPartMachine inputBus2, ItemBusPartMachine outputBus1,
                             FluidHatchPartMachine outputHatch1) {}

    /**
     * Retrieves the busses for this specific template and force a multiblock structure check
     * 
     * @param helper the GameTestHelper
     * @return the busses, in the BusHolder record.
     */
    private static BusHolder getBussesAndForm(GameTestHelper helper) {
        MultiblockControllerMachine controller = (MultiblockControllerMachine) getMetaMachine(
                helper.getBlockEntity(new BlockPos(1, 2, 0)));
        TestUtils.formMultiblock(controller);
        ItemBusPartMachine inputBus1 = (ItemBusPartMachine) getMetaMachine(
                helper.getBlockEntity(new BlockPos(2, 1, 0)));
        ItemBusPartMachine inputBus2 = (ItemBusPartMachine) getMetaMachine(
                helper.getBlockEntity(new BlockPos(2, 2, 0)));
        ItemBusPartMachine outputBus1 = (ItemBusPartMachine) getMetaMachine(
                helper.getBlockEntity(new BlockPos(0, 1, 0)));
        FluidHatchPartMachine outputHatch1 = (FluidHatchPartMachine) getMetaMachine(
                helper.getBlockEntity(new BlockPos(0, 2, 0)));
        return new BusHolder(inputBus1, inputBus2, outputBus1, outputHatch1);
    }

    // Test for putting both ingredients in the same bus.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationSingleBusTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus1.getInventory().setStackInSlot(1, new ItemStack(Blocks.ACACIA_WOOD));
        helper.succeedWhen(() -> {
            helper.assertTrue(
                    TestUtils.isItemStackEqual(busHolder.outputBus1.getInventory().getStackInSlot(0),
                            new ItemStack(Blocks.STONE)),
                    "Crafting items in same bus failed, expected STONE but was " +
                            busHolder.outputBus1.getInventory().getStackInSlot(0).getDisplayName());
        });
    }

    // Test for putting both ingredients in 2 busses without separation.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesWithoutSeparationTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.succeedWhen(() -> {
            helper.assertTrue(
                    TestUtils.isItemStackEqual(busHolder.outputBus1.getInventory().getStackInSlot(0),
                            new ItemStack(Blocks.STONE)),
                    "Crafting items in different busses with no color failed, expected STONE but was " +
                            busHolder.outputBus1.getInventory().getStackInSlot(0).getDisplayName());
        });
    }

    // Test for putting both ingredients in 2 busses with one undyed and one dyed.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesWithOneColorTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setPaintingColor(DyeColor.BLACK.getTextColor());
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.succeedWhen(() -> {
            helper.assertTrue(
                    TestUtils.isItemStackEqual(busHolder.outputBus1.getInventory().getStackInSlot(0),
                            new ItemStack(Blocks.STONE)),
                    "Crafting items in different busses with no color failed, expected STONE but was " +
                            busHolder.outputBus1.getInventory().getStackInSlot(0).getDisplayName());
        });
    }

    // Test for putting both ingredients in 2 busses with both dyed the same color.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesWithTheSameColorTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setPaintingColor(DyeColor.BLACK.getTextColor());
        busHolder.inputBus2.setPaintingColor(DyeColor.BLACK.getTextColor());
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.succeedWhen(() -> {
            helper.assertTrue(
                    TestUtils.isItemStackEqual(busHolder.outputBus1.getInventory().getStackInSlot(0),
                            new ItemStack(Blocks.STONE)),
                    "Crafting items in different busses with no color failed, expected STONE but was " +
                            busHolder.outputBus1.getInventory().getStackInSlot(0).getDisplayName());
        });
    }

    // Test for putting both ingredients in 2 busses with two dyed different colors.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesWithDifferentColorsTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setPaintingColor(DyeColor.BLACK.getTextColor());
        busHolder.inputBus2.setPaintingColor(DyeColor.BLUE.getTextColor());
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.failIfEver(() -> {
            helper.assertTrue(busHolder.outputBus1.getInventory().getStackInSlot(0).isEmpty(),
                    "Crafting items in busses with different colors succeeded but shouldn't have");
        });
        helper.succeed();
    }

    // Test for putting both ingredients in 2 busses with one distinct.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesOneDistinctTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setDistinct(true);
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.failIfEver(() -> {
            helper.assertTrue(busHolder.outputBus1.getInventory().getStackInSlot(0).isEmpty(),
                    "Crafting items in busses with distinct succeeded but shouldn't have");
        });
        helper.succeed();
    }

    // Test for putting both ingredients in 2 busses with both distinct.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesTwoDistinctTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setDistinct(true);
        busHolder.inputBus2.setDistinct(true);
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.failIfEver(() -> {
            helper.assertTrue(busHolder.outputBus1.getInventory().getStackInSlot(0).isEmpty(),
                    "Crafting items in busses with distinct succeeded but shouldn't have");
        });
        helper.succeed();
    }

    // Test for putting both ingredients in 2 busses with two distinct and dyed different colors.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesTwoDistinctAndColoredTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setDistinct(true);
        busHolder.inputBus2.setDistinct(true);
        busHolder.inputBus1.setPaintingColor(DyeColor.BLACK.getTextColor());
        busHolder.inputBus2.setPaintingColor(DyeColor.BLUE.getTextColor());
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.failIfEver(() -> {
            helper.assertTrue(busHolder.outputBus1.getInventory().getStackInSlot(0).isEmpty(),
                    "Crafting items in busses with distinct succeeded but shouldn't have");
        });
        helper.succeed();
    }

    // Test for putting both ingredients in 2 busses with one distinct and one colored.
    @GameTest(template = "lcr_input_separation", batch = "InputSeparation", setupTicks = 40, timeoutTicks = 200)
    public static void inputSeparationBothBussesOneDistinctOneColoredTest(GameTestHelper helper) {
        BusHolder busHolder = getBussesAndForm(helper);
        busHolder.inputBus1.setDistinct(true);
        busHolder.inputBus2.setPaintingColor(DyeColor.BLUE.getTextColor());
        busHolder.inputBus1.getInventory().setStackInSlot(0, new ItemStack(Blocks.COBBLESTONE));
        busHolder.inputBus2.getInventory().setStackInSlot(0, new ItemStack(Blocks.ACACIA_WOOD));
        helper.failIfEver(() -> {
            helper.assertTrue(busHolder.outputBus1.getInventory().getStackInSlot(0).isEmpty(),
                    "Crafting items in busses with distinct succeeded but shouldn't have");
        });
        helper.succeed();
    }
}
