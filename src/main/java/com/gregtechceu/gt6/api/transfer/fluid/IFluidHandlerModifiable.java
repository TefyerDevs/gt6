package com.gregtechceu.gt6.api.transfer.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidHandlerModifiable extends IFluidHandler {

    void setFluidInTank(int tank, FluidStack stack);

    default boolean supportsFill(int tank) {
        return true;
    }

    default boolean supportsDrain(int tank) {
        return true;
    }
}
