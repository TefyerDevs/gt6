package com.gregtechceu.gt6.api.fluids.attribute;

import com.gregtechceu.gt6.Gregtech;

import net.minecraft.network.chat.Component;

public final class FluidAttributes {

    /**
     * Attribute for acidic fluids.
     */
    public static final FluidAttribute ACID = new FluidAttribute(Gregtech.id("acid"),
            list -> list.accept(Component.translatable("gt6.fluid.type_acid.tooltip")),
            list -> list.accept(Component.translatable("gt6.fluid_pipe.acid_proof")));

    private FluidAttributes() {}
}
