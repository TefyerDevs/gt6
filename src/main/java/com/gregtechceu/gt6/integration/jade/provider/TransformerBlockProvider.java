package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.MetaMachine;
import com.gregtechceu.gt6.common.machine.electric.TransformerMachine;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class TransformerBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return Gregtech.id("transformer");
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof IMachineBlockEntity blockEntity) {
            MetaMachine machine = blockEntity.getMetaMachine();
            if (machine instanceof TransformerMachine transformer) {
                compoundTag.putInt("side", transformer.getFrontFacing().get3DDataValue());
                compoundTag.putBoolean("transformUp", transformer.isTransformUp());
                compoundTag.putInt("baseAmp", transformer.getBaseAmp());
                compoundTag.putInt("baseVoltage", transformer.getTier());
            }
        }
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof IMachineBlockEntity blockEntity) {
            MetaMachine machine = blockEntity.getMetaMachine();
            if (machine instanceof TransformerMachine transformer) {
                boolean transformUp = blockAccessor.getServerData().getBoolean("transformUp");
                int voltage = blockAccessor.getServerData().getInt("baseVoltage");
                int amp = blockAccessor.getServerData().getInt("baseAmp");
                if (transformUp) {
                    tooltip.add(Component.translatable("gt6.top.transform_up",
                            (GTValues.VNF[voltage] + " §r(" + amp * 4 + "A) -> " + GTValues.VNF[voltage + 1] + " §r(" +
                                    amp +
                                    "A)")));
                } else {
                    tooltip.add(Component.translatable("gt6.top.transform_down",
                            (GTValues.VNF[voltage + 1] + " §r(" + amp + "A) -> " + GTValues.VNF[voltage] + " §r(" +
                                    amp * 4 +
                                    "A)")));
                }

                if (blockAccessor.getHitResult().getDirection() ==
                        Direction.from3DDataValue(blockAccessor.getServerData().getInt("side"))) {
                    tooltip.add(
                            Component.translatable(
                                    (transformUp ? "gt6.top.transform_output" : "gt6.top.transform_input"),
                                    (GTValues.VNF[voltage + 1] + " §r(" + amp + "A)")));
                } else {
                    tooltip.add(
                            Component.translatable(
                                    (transformUp ? "gt6.top.transform_input" : "gt6.top.transform_output"),
                                    (GTValues.VNF[voltage] + " §r(" + amp * 4 + "A)")));
                }
            }
        }
    }
}
