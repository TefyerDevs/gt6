package com.gregtechceu.gt6.common.item;

import com.gregtechceu.gt6.api.item.component.IRecipeRemainder;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class ItemFluidContainer implements IRecipeRemainder {

    @Override
    public ItemStack getRecipeRemained(ItemStack itemStack) {
        return FluidUtil.getFluidHandler(itemStack).map(handler -> {
            var drained = handler.drain(FluidType.BUCKET_VOLUME, FluidAction.SIMULATE);
            if (drained.getAmount() != FluidType.BUCKET_VOLUME) return ItemStack.EMPTY;
            handler.drain(FluidType.BUCKET_VOLUME, FluidAction.EXECUTE);
            return handler.getContainer();
        }).orElse(itemStack);
    }
}
