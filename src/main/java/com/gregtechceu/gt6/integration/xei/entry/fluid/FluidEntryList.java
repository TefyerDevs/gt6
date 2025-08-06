package com.gregtechceu.gt6.integration.xei.entry.fluid;

import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public sealed interface FluidEntryList permits FluidStackList, FluidTagList {

    List<FluidStack> getStacks();

    boolean isEmpty();
}
