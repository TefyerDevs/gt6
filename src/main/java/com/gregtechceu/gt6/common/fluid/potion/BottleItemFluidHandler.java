package com.gregtechceu.gt6.common.fluid.potion;

import com.gregtechceu.gt6.api.misc.forge.FilteredFluidHandlerItemStackSimple;
import com.gregtechceu.gt6.data.recipe.CustomTags;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

public class BottleItemFluidHandler extends FilteredFluidHandlerItemStackSimple {

    public BottleItemFluidHandler(@NotNull ItemStack container) {
        super(container, PotionFluidHelper.BOTTLE_AMOUNT, s -> s.getFluid().is(CustomTags.POTION_FLUIDS));
    }

    @Override
    protected void setFluid(FluidStack fluid) {
        if (!fluid.isEmpty()) {
            container = PotionUtils.setPotion(new ItemStack(Items.POTION), PotionUtils.getPotion(fluid.getTag()));
        }
    }
}
