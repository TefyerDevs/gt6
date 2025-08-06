package com.gregtechceu.gt6.common.machine.trait;

import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.capability.recipe.IO;
import com.gregtechceu.gt6.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.api.data.worldgen.bedrockore.BedrockOreVeinSavedData;
import com.gregtechceu.gt6.api.data.worldgen.bedrockore.OreVeinWorldEntry;
import com.gregtechceu.gt6.api.data.worldgen.bedrockore.WeightedMaterial;
import com.gregtechceu.gt6.api.machine.trait.RecipeLogic;
import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.RecipeHelper;
import com.gregtechceu.gt6.common.machine.multiblock.electric.BedrockOreMinerMachine;
import com.gregtechceu.gt6.config.ConfigHolder;
import com.gregtechceu.gt6.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BedrockOreMinerLogic extends RecipeLogic {

    public static final int MAX_PROGRESS = 20;

    @Getter
    @Nullable
    private List<WeightedMaterial> veinMaterials;

    public BedrockOreMinerLogic(BedrockOreMinerMachine machine) {
        super(machine);
    }

    @Override
    public BedrockOreMinerMachine getMachine() {
        return (BedrockOreMinerMachine) super.getMachine();
    }

    @Override
    public void findAndHandleRecipe() {
        if (getMachine().getLevel() instanceof ServerLevel serverLevel) {
            lastRecipe = null;
            var data = BedrockOreVeinSavedData.getOrCreate(serverLevel);
            if (veinMaterials == null) {
                this.veinMaterials = data.getOreInChunk(getChunkX(), getChunkZ());
                if (this.veinMaterials == null) {
                    if (subscription != null) {
                        subscription.unsubscribe();
                        subscription = null;
                    }
                    return;
                }
            }
            var match = getOreMinerRecipe();
            if (match != null) {
                if (RecipeHelper.matchContents(this.machine, match).isSuccess()) {
                    setupRecipe(match);
                }
            }
        }
    }

    @Nullable
    private GTRecipe getOreMinerRecipe() {
        if (getMachine().getLevel() instanceof ServerLevel serverLevel && veinMaterials != null) {
            WeightedMaterial wm = GTUtil.getRandomItem(serverLevel.random, veinMaterials);
            if (wm == null) return null;
            Material material = wm.material();
            ItemStack stack = ChemicalHelper.get(TagPrefix.get(ConfigHolder.INSTANCE.machines.bedrockOreDropTagPrefix),
                    material, getOreToProduce());
            if (stack.isEmpty()) stack = ChemicalHelper.get(TagPrefix.crushed, material, getOreToProduce()); // backup
                                                                                                             // 1:
                                                                                                             // crushed;
                                                                                                             // if raw
                                                                                                             // ore
                                                                                                             // doesn't
                                                                                                             // exist
            if (stack.isEmpty()) stack = ChemicalHelper.get(TagPrefix.gem, material, getOreToProduce()); // backup 2:
                                                                                                         // gem; if
                                                                                                         // crushed ore
                                                                                                         // doesn't
                                                                                                         // exist
            if (stack.isEmpty()) stack = ChemicalHelper.get(TagPrefix.ore, material, getOreToProduce()); // backup 3:
                                                                                                         // normal ore;
                                                                                                         // if gem
                                                                                                         // doesn't
                                                                                                         // exist.
            if (stack.isEmpty()) stack = ChemicalHelper.get(TagPrefix.dust, material, getOreToProduce()); // backup 4:
                                                                                                          // fallback to
                                                                                                          // dust
            if (stack.isEmpty()) {
                return null;
            }
            var recipe = GTRecipeBuilder.ofRaw()
                    .duration(MAX_PROGRESS)
                    .EUt(GTValues.VA[getMachine().getEnergyTier()])
                    .outputItems(stack)
                    .buildRawRecipe();
            if (RecipeHelper.matchContents(getMachine(), recipe).isSuccess()) {
                return recipe;
            }
        }
        return null;
    }

    private int getOreToProduce(OreVeinWorldEntry entry) {
        var definition = entry.getDefinition();
        if (definition != null) {
            int depletedYield = definition.depletedYield();
            int regularYield = entry.getOreYield();
            int remainingOperations = entry.getOperationsRemaining();

            int produced = Math.max(depletedYield,
                    regularYield * remainingOperations / BedrockOreVeinSavedData.MAXIMUM_VEIN_OPERATIONS);
            produced *= BedrockOreMinerMachine.getRigMultiplier(getMachine().getTier());

            // Overclocks produce 50% more ore
            if (isOverclocked()) {
                produced = produced * 3 / 2;
            }
            return produced;
        }
        return 0;
    }

    public int getOreToProduce() {
        if (getMachine().getLevel() instanceof ServerLevel serverLevel && veinMaterials != null) {
            var data = BedrockOreVeinSavedData.getOrCreate(serverLevel);
            return getOreToProduce(data.getOreVeinWorldEntry(getChunkX(), getChunkZ()));
        }
        return 0;
    }

    @Override
    public void onRecipeFinish() {
        machine.afterWorking();
        if (lastRecipe != null) {
            RecipeHelper.handleRecipeIO(this.machine, lastRecipe, IO.OUT, this.chanceCaches);
        }
        depleteVein();
        // try it again
        var match = getOreMinerRecipe();
        if (match != null) {
            if (RecipeHelper.matchContents(this.machine, match).isSuccess()) {
                setupRecipe(match);
                return;
            }
        }
        if (suspendAfterFinish) {
            setStatus(Status.SUSPEND);
            suspendAfterFinish = false;
        } else {
            setStatus(Status.IDLE);
        }
        progress = 0;
        duration = 0;
    }

    protected void depleteVein() {
        if (getMachine().getLevel() instanceof ServerLevel serverLevel) {
            int chance = BedrockOreMinerMachine.getDepletionChance(getMachine().getTier());
            var data = BedrockOreVeinSavedData.getOrCreate(serverLevel);
            // chance to deplete based on the rig
            if (chance == 1 || GTValues.RNG.nextInt(chance) == 0) {
                data.depleteVein(getChunkX(), getChunkZ(), 0, false);
            }
        }
    }

    protected boolean isOverclocked() {
        return getMachine().getEnergyTier() > getMachine().getTier();
    }

    private int getChunkX() {
        return SectionPos.blockToSectionCoord(getMachine().getPos().getX());
    }

    private int getChunkZ() {
        return SectionPos.blockToSectionCoord(getMachine().getPos().getZ());
    }
}
