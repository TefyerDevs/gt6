package com.gregtechceu.gt6.common.machine.electric;

import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.SimpleTieredMachine;
import com.gregtechceu.gt6.common.data.machines.GTMachineUtils;

public class RockCrusherMachine extends SimpleTieredMachine {

    public RockCrusherMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, GTMachineUtils.defaultTankSizeFunction, args);
    }

    @Override
    public boolean shouldWeatherOrTerrainExplosion() {
        return false;
    }
}
