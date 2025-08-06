package com.gregtechceu.gt6.api.machine.feature;

import com.gregtechceu.gt6.api.machine.trait.NotifiableItemStackHandler;

public interface IHasCircuitSlot {

    default boolean isCircuitSlotEnabled() {
        return true;
    }

    NotifiableItemStackHandler getCircuitInventory();
}
