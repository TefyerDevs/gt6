package com.gregtechceu.gt6.api.machine.feature;

import com.gregtechceu.gt6.api.machine.MetaMachine;

public interface IMachineFeature {

    default MetaMachine self() {
        return (MetaMachine) this;
    }
}
