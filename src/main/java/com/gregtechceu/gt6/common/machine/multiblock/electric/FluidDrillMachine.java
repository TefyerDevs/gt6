package com.gregtechceu.gt6.common.machine.multiblock.electric;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.capability.energy.types.IEnergyContainer;
import com.gregtechceu.gt6.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gt6.api.capability.recipe.IO;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.feature.ITieredMachine;
import com.gregtechceu.gt6.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gt6.api.machine.trait.RecipeLogic;
import com.gregtechceu.gt6.api.misc.EnergyContainerList;
import com.gregtechceu.gt6.common.data.GTBlocks;
import com.gregtechceu.gt6.common.data.GTMaterialBlocks;
import com.gregtechceu.gt6.common.data.GTMaterials;
import com.gregtechceu.gt6.common.machine.trait.FluidDrillLogic;
import com.gregtechceu.gt6.utils.FormattingUtil;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FluidDrillMachine extends WorkableElectricMultiblockMachine implements ITieredMachine {

    @Getter
    private final int tier;

    public FluidDrillMachine(IMachineBlockEntity holder, int tier) {
        super(holder);
        this.tier = tier;
    }

    @Override
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new FluidDrillLogic(this);
    }

    @NotNull
    @Override
    public FluidDrillLogic getRecipeLogic() {
        return (FluidDrillLogic) super.getRecipeLogic();
    }

    public int getEnergyTier() {
        var energyContainer = this.getCapabilitiesFlat(IO.IN, EURecipeCapability.CAP);
        if (energyContainer == null) return this.tier;
        var energyCont = new EnergyContainerList(energyContainer.stream().filter(IEnergyContainer.class::isInstance)
                .map(IEnergyContainer.class::cast).toList());

        return Math.min(this.tier + 1, Math.max(this.tier, GTUtil.getFloorTierByVoltage(energyCont.getInputVoltage())));
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (isFormed()) {
            int energyContainer = getEnergyTier();
            long maxVoltage = GTValues.V[energyContainer];
            String voltageName = GTValues.VNF[energyContainer];
            textList.add(Component.translatable("gt6.multiblock.max_energy_per_tick", maxVoltage, voltageName));

            if (getRecipeLogic().getVeinFluid() != null) {
                // Fluid name
                Fluid drilledFluid = getRecipeLogic().getVeinFluid();
                Component fluidInfo = drilledFluid.getFluidType().getDescription().copy()
                        .withStyle(ChatFormatting.GREEN);
                textList.add(Component.translatable("gt6.multiblock.fluid_rig.drilled_fluid", fluidInfo)
                        .withStyle(ChatFormatting.GRAY));

                // Fluid amount
                Component amountInfo = Component.literal(FormattingUtil.formatNumbers(
                        getRecipeLogic().getFluidToProduce() * 20L / FluidDrillLogic.MAX_PROGRESS) +
                        " mB/s").withStyle(ChatFormatting.BLUE);
                textList.add(Component.translatable("gt6.multiblock.fluid_rig.fluid_amount", amountInfo)
                        .withStyle(ChatFormatting.GRAY));
            } else {
                Component noFluid = Component.translatable("gt6.multiblock.fluid_rig.no_fluid_in_area")
                        .withStyle(ChatFormatting.RED);
                textList.add(Component.translatable("gt6.multiblock.fluid_rig.drilled_fluid", noFluid)
                        .withStyle(ChatFormatting.GRAY));
            }
        } else {
            Component tooltip = Component.translatable("gt6.multiblock.invalid_structure.tooltip")
                    .withStyle(ChatFormatting.GRAY);
            textList.add(Component.translatable("gt6.multiblock.invalid_structure")
                    .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        }
    }

    public static int getDepletionChance(int tier) {
        if (tier == GTValues.MV)
            return 1;
        if (tier == GTValues.HV)
            return 2;
        if (tier == GTValues.EV)
            return 8;
        return 1;
    }

    public static int getRigMultiplier(int tier) {
        if (tier == GTValues.MV)
            return 1;
        if (tier == GTValues.HV)
            return 16;
        if (tier == GTValues.EV)
            return 64;
        return 1;
    }

    public static Block getCasingState(int tier) {
        if (tier == GTValues.MV)
            return GTBlocks.CASING_STEEL_SOLID.get();
        if (tier == GTValues.HV)
            return GTBlocks.CASING_TITANIUM_STABLE.get();
        if (tier == GTValues.EV)
            return GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get();
        return GTBlocks.CASING_STEEL_SOLID.get();
    }

    @SuppressWarnings("DataFlowIssue")
    public static Block getFrameState(int tier) {
        if (tier == GTValues.MV)
            return GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Steel).get();
        if (tier == GTValues.HV)
            return GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Titanium).get();
        if (tier == GTValues.EV)
            return GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TungstenSteel).get();
        return GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Steel).get();
    }

    public static ResourceLocation getBaseTexture(int tier) {
        if (tier == GTValues.MV)
            return Gregtech.id("block/casings/solid/machine_casing_solid_steel");
        if (tier == GTValues.HV)
            return Gregtech.id("block/casings/solid/machine_casing_stable_titanium");
        if (tier == GTValues.EV)
            return Gregtech.id("block/casings/solid/machine_casing_robust_tungstensteel");
        return Gregtech.id("block/casings/solid/machine_casing_solid_steel");
    }
}
