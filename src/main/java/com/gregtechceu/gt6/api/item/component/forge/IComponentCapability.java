package com.gregtechceu.gt6.api.item.component.forge;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import org.jetbrains.annotations.NotNull;

public interface IComponentCapability {

    <T> @NotNull LazyOptional<T> getCapability(ItemStack itemStack, @NotNull Capability<T> cap);
}
