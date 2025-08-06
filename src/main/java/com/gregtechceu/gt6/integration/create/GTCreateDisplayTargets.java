package com.gregtechceu.gt6.integration.create;

import com.gregtechceu.gt6.api.registry.GTRegistries;
import com.gregtechceu.gt6.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gt6.common.registry.GTRegistration;
import com.gregtechceu.gt6.integration.create.display.ComputerMonitorCoverDisplayTarget;

import net.minecraft.core.registries.Registries;

import com.simibubi.create.api.behaviour.display.DisplayTarget;
import com.simibubi.create.api.registry.registrate.SimpleBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;

import java.util.function.Supplier;

public class GTCreateDisplayTargets {

    public static final RegistryEntry<ComputerMonitorCoverDisplayTarget> COMPUTER_MONITOR_COVER = registerToAllMachines(
            "computer_monitor_cover", ComputerMonitorCoverDisplayTarget::new);

    @SuppressWarnings("SameParameterValue")
    private static <T extends DisplayTarget> RegistryEntry<T> registerToAllMachines(String name, Supplier<T> supplier) {
        SimpleBuilder<DisplayTarget, T, GTRegistrate> builder = GTCreateIntegration
                .displayTarget(GTRegistration.REGISTRATE, name, supplier);
        builder.onRegisterAfter(
                Registries.BLOCK_ENTITY_TYPE,
                target -> GTRegistries.MACHINES.entries().forEach(
                        (entry) -> DisplayTarget.BY_BLOCK_ENTITY.register(
                                entry.getValue().getBlockEntityType(),
                                target)));
        return builder.register();
    }

    public static void init() {}
}
