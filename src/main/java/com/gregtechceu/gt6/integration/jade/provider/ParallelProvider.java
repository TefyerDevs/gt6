package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gt6.api.capability.IParallelHatch;
import com.gregtechceu.gt6.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gt6.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gt6.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class ParallelProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("parallel")) {
            int parallel = blockAccessor.getServerData().getInt("parallel");
            if (parallel > 1) {
                Component parallels = Component.literal(FormattingUtil.formatNumbers(parallel))
                        .withStyle(ChatFormatting.DARK_PURPLE);
                String key = "gt6.multiblock.parallel";
                if (blockAccessor.getServerData().getBoolean("exact")) key += ".exact";
                iTooltip.add(Component.translatable(key, parallels));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof MetaMachineBlockEntity blockEntity) {
            if (blockEntity.getMetaMachine() instanceof IParallelHatch parallelHatch) {
                compoundTag.putInt("parallel", parallelHatch.getCurrentParallel());
            } else if (blockEntity.getMetaMachine() instanceof IMultiController controller) {
                if (controller instanceof IRecipeLogicMachine rlm &&
                        rlm.getRecipeLogic().isActive() &&
                        rlm.getRecipeLogic().getLastRecipe() != null) {
                    compoundTag.putInt("parallel", rlm.getRecipeLogic().getLastRecipe().parallels);
                    compoundTag.putBoolean("exact", true);
                } else {
                    controller.getParallelHatch()
                            .ifPresent(parallelHatch -> compoundTag.putInt("parallel",
                                    parallelHatch.getCurrentParallel()));
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return GTCEu.id("parallel_info");
    }
}
