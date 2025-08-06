package com.gregtechceu.gt6.api.machine.multiblock;

import org.jetbrains.annotations.NotNull;

public interface IBatteryData {

    int getTier();

    long getCapacity();

    @NotNull
    String getBatteryName();
}
