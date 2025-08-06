package com.gregtechceu.gt6.common.item;

import com.gregtechceu.gt6.api.item.component.IInteractionItem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;

public class TapeBehaviour implements IInteractionItem {

    public TapeBehaviour() {}

    @Override
    public boolean sneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }
}
