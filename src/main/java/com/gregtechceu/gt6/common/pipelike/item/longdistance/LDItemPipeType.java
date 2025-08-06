package com.gregtechceu.gt6.common.pipelike.item.longdistance;

import com.gregtechceu.gt6.api.pipenet.longdistance.LongDistancePipeType;
import com.gregtechceu.gt6.config.ConfigHolder;

public class LDItemPipeType extends LongDistancePipeType {

    public static final LDItemPipeType INSTANCE = new LDItemPipeType();

    private LDItemPipeType() {
        super("item");
    }

    @Override
    public int getMinLength() {
        return ConfigHolder.INSTANCE.machines.ldItemPipeMinDistance;
    }
}
