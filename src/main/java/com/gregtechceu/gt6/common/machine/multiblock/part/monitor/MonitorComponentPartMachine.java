package com.gregtechceu.gt6.common.machine.multiblock.part.monitor;

import com.gregtechceu.gt6.api.capability.IMonitorComponent;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.multiblock.part.MultiblockPartMachine;

public abstract class MonitorComponentPartMachine extends MultiblockPartMachine implements IMonitorComponent {

    public MonitorComponentPartMachine(IMachineBlockEntity holder) {
        super(holder);
    }
}
