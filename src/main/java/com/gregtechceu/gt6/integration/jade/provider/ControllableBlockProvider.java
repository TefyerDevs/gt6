package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.capability.GTCapabilityHelper;
import com.gregtechceu.gt6.api.capability.IControllable;

import net.minecraft.ChatFormatting;
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

public class ControllableBlockProvider extends CapabilityBlockProvider<IControllable> {

    public ControllableBlockProvider() {
        super(GTCEu.id("controllable_provider"));
    }

    @Nullable
    @Override
    protected IControllable getCapability(Level level, BlockPos pos, @Nullable Direction side) {
        return GTCapabilityHelper.getControllable(level, pos, side);
    }

    @Override
    protected void write(CompoundTag data, IControllable capability) {
        data.putBoolean("WorkingEnabled", capability.isWorkingEnabled());
    }

    @Override
    protected void addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block,
                              BlockEntity blockEntity, IPluginConfig config) {
        if (capData.contains("WorkingEnabled") && !capData.getBoolean("WorkingEnabled")) {
            tooltip.add(Component.translatable("gt6.top.working_disabled").withStyle(ChatFormatting.YELLOW));
        }
    }
}
