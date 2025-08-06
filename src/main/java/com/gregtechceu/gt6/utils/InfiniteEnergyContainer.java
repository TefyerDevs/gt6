package com.gregtechceu.gt6.utils;

import com.gregtechceu.gt6.api.machine.MetaMachine;
import com.gregtechceu.gt6.api.machine.trait.NotifiableEnergyContainer;

public class InfiniteEnergyContainer extends NotifiableEnergyContainer {

    public InfiniteEnergyContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage,
                                   long maxOutputVoltage, long maxOutputAmperage) {
        super(machine, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);
    }

    @Override
    public long getEnergyStored() {
        return getEnergyCapacity();
    }
}
