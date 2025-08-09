package com.gregtechceu.gt6.integration.cctweaked.peripherals;

import com.gregtechceu.gt6.api.capability.energy.IEnergyBaseProvider;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.GenericPeripheral;

public class EnergyInfoPeripheral implements GenericPeripheral {

    public String id() {
        return "gt6:energy_info";
    }

    @LuaFunction
    public static MethodResult getEnergyStored(IEnergyBaseProvider infoProvider) {
        return MethodResult.of(infoProvider.getEnergyInfo().stored());
    }

    @LuaFunction
    public static MethodResult getEnergyCapacity(IEnergyBaseProvider infoProvider) {
        return MethodResult.of(infoProvider.getEnergyInfo().capacity());
    }

    @LuaFunction
    public static MethodResult getInputPerSec(IEnergyBaseProvider changeProvider) {
        return MethodResult.of(changeProvider.getInputPerSec());
    }

    @LuaFunction
    public static MethodResult getOutputPerSec(IEnergyBaseProvider changeProvider) {
        return MethodResult.of(changeProvider.getOutputPerSec());
    }
}
