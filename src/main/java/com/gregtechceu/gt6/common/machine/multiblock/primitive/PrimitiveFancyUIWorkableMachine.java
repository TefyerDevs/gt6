package com.gregtechceu.gt6.common.machine.multiblock.primitive;

import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.feature.IFancyUIMachine;

public class PrimitiveFancyUIWorkableMachine extends PrimitiveWorkableMachine implements IFancyUIMachine {

    public PrimitiveFancyUIWorkableMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }
}
