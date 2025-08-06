package com.gregtechceu.gt6.api.capability;

import com.gregtechceu.gt6.api.machine.trait.NotifiableComputationContainer;

/**
 * Used in conjunction with {@link NotifiableComputationContainer}.
 */
public interface IOpticalComputationReceiver {

    IOpticalComputationProvider getComputationProvider();
}
