package com.gregtechceu.gt6.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EntityUtils {
    public static ItemEntity summonItemEntity(Level level, BlockPos blockPos, Item item){
        ItemEntity itemEntity = new ItemEntity(level,blockPos.getX(),blockPos.getY(), blockPos.getZ(),new ItemStack(item));
        level.addFreshEntity(itemEntity);
        return itemEntity;
    }
}
