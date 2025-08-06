package com.gregtechceu.gt6.integration.cctweaked;

import com.gregtechceu.gt6.api.capability.forge.GTCapability;
import com.gregtechceu.gt6.integration.cctweaked.peripherals.*;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.ForgeComputerCraftAPI;

public class CCTweakedPlugin {

    public static void init() {
        ComputerCraftAPI.registerGenericSource(new ControllablePeripheral());
        ComputerCraftAPI.registerGenericSource(new EnergyInfoPeripheral());
        ComputerCraftAPI.registerGenericSource(new TurbineMachinePeripheral());
        ComputerCraftAPI.registerGenericSource(new WorkablePeripheral());
        ComputerCraftAPI.registerGenericSource(new CoverHolderPeripheral());
        ForgeComputerCraftAPI.registerGenericCapability(GTCapability.CAPABILITY_CONTROLLABLE);
        ForgeComputerCraftAPI.registerGenericCapability(GTCapability.CAPABILITY_ENERGY_INFO_PROVIDER);
        ForgeComputerCraftAPI.registerGenericCapability(GTCapability.CAPABILITY_TURBINE_MACHINE);
        ForgeComputerCraftAPI.registerGenericCapability(GTCapability.CAPABILITY_WORKABLE);
        ForgeComputerCraftAPI.registerGenericCapability(GTCapability.CAPABILITY_COVERABLE);
    }
}
