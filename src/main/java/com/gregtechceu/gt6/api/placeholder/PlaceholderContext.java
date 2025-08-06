package com.gregtechceu.gt6.api.placeholder;

import com.gregtechceu.gt6.api.cover.CoverBehavior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.Nullable;

public record PlaceholderContext(Level level,
                                 BlockPos pos,
                                 Direction side,
                                 @Nullable ItemStackHandler itemStackHandler,
                                 @Nullable CoverBehavior cover,
                                 @Nullable MultiLineComponent previousText) {}
