package com.gregtechceu.gt6.core.mixins.rei;

import com.gregtechceu.gt6.client.TooltipsHandler;
import com.gregtechceu.gt6.utils.GTMath;

import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.plugin.client.entry.FluidEntryDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidEntryDefinition.FluidEntryRenderer.class)
public class FluidEntryRendererMixin {

    @Inject(method = "getTooltip",
            at = @At(value = "TAIL"),
            remap = false,
            require = 0)
    private void gt6$addMaterialTooltip(EntryStack<FluidStack> entry, TooltipContext context,
                                          CallbackInfoReturnable<Tooltip> cir) {
        FluidStack stack = entry.getValue();
        TooltipsHandler.appendFluidTooltips(new net.minecraftforge.fluids.FluidStack(stack.getFluid(),
                GTMath.saturatedCast(stack.getAmount()), stack.getTag()),
                cir.getReturnValue()::add,
                context.getFlag());
    }
}
