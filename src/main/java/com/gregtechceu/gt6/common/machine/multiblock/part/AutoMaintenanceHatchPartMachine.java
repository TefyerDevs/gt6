package com.gregtechceu.gt6.common.machine.multiblock.part;

import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.feature.multiblock.IMaintenanceMachine;
import com.gregtechceu.gt6.api.machine.multiblock.part.TieredPartMachine;

public class AutoMaintenanceHatchPartMachine extends TieredPartMachine implements IMaintenanceMachine {

    public AutoMaintenanceHatchPartMachine(IMachineBlockEntity holder) {
        super(holder, GTValues.HV);
    }

    @Override
    public void setTaped(boolean ignored) {}

    @Override
    public boolean isTaped() {
        return false;
    }

    @Override
    public boolean isFullAuto() {
        return true;
    }

    @Override
    public byte startProblems() {
        return NO_PROBLEMS;
    }

    @Override
    public byte getMaintenanceProblems() {
        return NO_PROBLEMS;
    }

    @Override
    public void setMaintenanceProblems(byte problems) {}

    @Override
    public int getTimeActive() {
        return 0;
    }

    @Override
    public void setTimeActive(int time) {}
}
