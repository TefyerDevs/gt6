package com.gregtechceu.gt6.client.model.machine.multipart;

import com.gregtechceu.gt6.api.machine.MachineDefinition;
import com.gregtechceu.gt6.client.model.machine.MachineRenderState;

import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Predicate;

@FunctionalInterface
public interface PartCondition {

    PartCondition TRUE = (definition) -> state -> true;
    PartCondition FALSE = (definition) -> state -> false;

    Predicate<MachineRenderState> getPredicate(StateDefinition<MachineDefinition, MachineRenderState> def);
}
