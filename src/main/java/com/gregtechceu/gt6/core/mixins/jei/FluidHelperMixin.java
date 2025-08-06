package com.gregtechceu.gt6.core.mixins.jei;

import com.gregtechceu.gt6.client.TooltipsHandler;

import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.forge.platform.FluidHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidHelper.class)
public class FluidHelperMixin {

    @Inject(method = "getTooltip(Lmezz/jei/api/gui/builder/ITooltipBuilder;Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/item/TooltipFlag;)V",
            at = @At("TAIL"),
            remap = false,
            require = 0)
    private void gt6$injectFluidTooltips(ITooltipBuilder tooltip, FluidStack ingredient, TooltipFlag tooltipFlag,
                                           CallbackInfo ci) {
        TooltipsHandler.appendFluidTooltips(ingredient, tooltip::add, tooltipFlag);
    }
}
