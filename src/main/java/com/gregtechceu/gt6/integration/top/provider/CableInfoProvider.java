package com.gregtechceu.gt6.integration.top.provider;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.common.block.CableBlock;
import com.gregtechceu.gt6.common.blockentity.CableBlockEntity;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import mcjty.theoneprobe.api.*;

import static com.gregtechceu.gt6.utils.FormattingUtil.DECIMAL_FORMAT_1F;

public class CableInfoProvider implements IProbeInfoProvider {

    @Override
    public ResourceLocation getID() {
        return Gregtech.id("cable_info");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level,
                             BlockState blockState, IProbeHitData iProbeHitData) {
        if (blockState.getBlock() instanceof CableBlock cableBlock) {
            CableBlockEntity cable = (CableBlockEntity) cableBlock.getPipeTile(level, iProbeHitData.getPos());
            if (cable != null) {
                long voltage = cable.getCurrentMaxVoltage();
                double amperage = cable.getAverageAmperage();
                IProbeInfo horizontalPane = iProbeInfo
                        .horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(Component.translatable("gt6.top.cable_voltage"));
                if (voltage != 0) {
                    horizontalPane.text(GTValues.VNF[GTUtil.getTierByVoltage(voltage)]).text(" / ");
                }
                horizontalPane.text(GTValues.VNF[GTUtil.getTierByVoltage(cable.getMaxVoltage())]);

                horizontalPane = iProbeInfo
                        .horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(Component.translatable("gt6.top.cable_amperage"));
                if (amperage != 0) {
                    horizontalPane.text(DECIMAL_FORMAT_1F.format(cable.getAverageAmperage()) + "A / ");
                }
                horizontalPane.text(DECIMAL_FORMAT_1F.format(cable.getMaxAmperage()) + "A");
            }
        }
    }
}
