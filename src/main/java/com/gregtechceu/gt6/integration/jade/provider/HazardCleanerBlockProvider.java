package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.feature.IEnvironmentalHazardCleaner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HazardCleanerBlockProvider extends CapabilityBlockProvider<IEnvironmentalHazardCleaner> {

    public HazardCleanerBlockProvider() {
        super(Gregtech.id("hazard_cleaner_provider"));
    }

    @Override
    protected @Nullable IEnvironmentalHazardCleaner getCapability(Level level, BlockPos pos, @Nullable Direction side) {
        return level.getBlockEntity(pos) instanceof MetaMachineBlockEntity mte &&
                mte.getMetaMachine() instanceof IEnvironmentalHazardCleaner cleaner ? cleaner : null;
    }

    @Override
    protected void write(CompoundTag data, IEnvironmentalHazardCleaner capability) {
        data.putFloat("Cleaned", capability.getRemovedLastSecond());
    }

    @Override
    protected void addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block,
                              BlockEntity blockEntity, IPluginConfig config) {
        float cleaned = capData.getFloat("Cleaned");
        if (cleaned > 0) {
            tooltip.add(Component.translatable("gt6.jade.cleaned_this_second", cleaned));
        }
    }
}
