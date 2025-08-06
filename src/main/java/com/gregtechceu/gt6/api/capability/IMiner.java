package com.gregtechceu.gt6.api.capability;

import com.gregtechceu.gt6.api.machine.feature.IMachineLife;
import com.gregtechceu.gt6.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gt6.common.machine.trait.miner.MinerLogic;

public interface IMiner extends IRecipeLogicMachine, IMachineLife {

    @Override
    MinerLogic getRecipeLogic();

    @Override
    default void onMachineRemoved() {
        getRecipeLogic().onRemove();
    }

    boolean drainInput(boolean simulate);

    static int getWorkingArea(int maximumRadius) {
        return maximumRadius * 2 + 1;
    }
}
