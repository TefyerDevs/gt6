package com.gregtechceu.gt6.integration.top.provider;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.machine.MetaMachine;
import com.gregtechceu.gt6.common.machine.electric.TransformerMachine;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;

public class TransformerInfoProvider implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level,
                             BlockState blockState, IProbeHitData iProbeHitData) {
        if (MetaMachine.getMachine(level, iProbeHitData.getPos()) instanceof TransformerMachine transformer) {

            boolean transformUp = transformer.isTransformUp();
            int voltage = transformer.getTier();
            int amp = transformer.getBaseAmp();
            int side = transformer.getFrontFacing().get3DDataValue();

            IProbeInfo verticalPane = iProbeInfo.vertical(iProbeInfo.defaultLayoutStyle().spacing(0));
            if (transformUp) {
                verticalPane.text(Component.translatable("gt6.top.transform_up",
                        (GTValues.VNF[voltage] + " §r(" + amp * 4 + "A) -> " + GTValues.VNF[voltage + 1] + " §r(" +
                                amp +
                                "A)")));
            } else {
                verticalPane.text(Component.translatable("gt6.top.transform_down",
                        (GTValues.VNF[voltage + 1] + " §r(" + amp + "A) -> " + GTValues.VNF[voltage] + " §r(" +
                                amp * 4 +
                                "A)")));
            }

            if (iProbeHitData.getSideHit() == Direction.from3DDataValue(side)) {
                verticalPane.text(
                        Component.translatable(
                                (transformUp ? "gt6.top.transform_output" : "gt6.top.transform_input"),
                                (GTValues.VNF[voltage + 1] + " §r(" + amp + "A)")));
            } else {
                verticalPane.text(
                        Component.translatable(
                                (transformUp ? "gt6.top.transform_input" : "gt6.top.transform_output"),
                                (GTValues.VNF[voltage] + " §r(" + amp * 4 + "A)")));
            }
        }
    }

    @Override
    public ResourceLocation getID() {
        return GTCEu.id("transformer_provider");
    }
}
