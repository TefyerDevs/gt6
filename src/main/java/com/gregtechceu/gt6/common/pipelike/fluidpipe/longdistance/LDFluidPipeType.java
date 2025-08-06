package com.gregtechceu.gt6.common.pipelike.fluidpipe.longdistance;

import com.gregtechceu.gt6.api.pipenet.longdistance.LongDistancePipeType;
import com.gregtechceu.gt6.config.ConfigHolder;

public class LDFluidPipeType extends LongDistancePipeType {

    public static final LDFluidPipeType INSTANCE = new LDFluidPipeType();

    private LDFluidPipeType() {
        super("fluid");
    }

    @Override
    public int getMinLength() {
        return ConfigHolder.INSTANCE.machines.ldFluidPipeMinDistance;
    }
}
