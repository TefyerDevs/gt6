package com.gregtechceu.gt6.api.item.component;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface IRecipeRemainder extends IItemComponent {

    ItemStack getRecipeRemained(ItemStack itemStack);
}
