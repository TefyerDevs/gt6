package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gt6.common.machine.electric.ConverterMachine;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class EnergyConverterModeProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("converterMode")) {
            boolean isFeToEu = blockAccessor.getServerData().getBoolean("converterMode");
            if (isFeToEu) {
                iTooltip.add(Component.translatable("gt6.top.convert_fe"));
            } else {
                iTooltip.add(Component.translatable("gt6.top.convert_eu"));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof MetaMachineBlockEntity blockEntity &&
                blockEntity.getMetaMachine() instanceof ConverterMachine converter) {
            compoundTag.putBoolean("converterMode", converter.isFeToEu());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Gregtech.id("energy_converter_provider");
    }
}
