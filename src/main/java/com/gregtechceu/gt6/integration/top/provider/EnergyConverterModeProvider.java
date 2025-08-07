package com.gregtechceu.gt6.integration.top.provider;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gt6.common.machine.electric.ConverterMachine;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;

public class EnergyConverterModeProvider implements IProbeInfoProvider {

    @Override
    public ResourceLocation getID() {
        return Gregtech.id("energy_converter_top");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level,
                             BlockState blockState, IProbeHitData iProbeHitData) {
        if (level.getBlockEntity(iProbeHitData.getPos()) instanceof MetaMachineBlockEntity blockEntity &&
                blockEntity.getMetaMachine() instanceof ConverterMachine converter) {
            if (converter.isFeToEu()) {
                iProbeInfo.text(Component.translatable("gt6.top.convert_fe"));
            } else {
                iProbeInfo.text(Component.translatable("gt6.top.convert_eu"));
            }
        }
    }
}
