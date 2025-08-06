package com.gregtechceu.gt6.api.machine.trait;

import com.gregtechceu.gt6.api.capability.recipe.IO;
import com.gregtechceu.gt6.api.capability.recipe.IRecipeHandler;

import com.lowdragmc.lowdraglib.syncdata.ISubscription;

public interface IRecipeHandlerTrait<K> extends IRecipeHandler<K> {

    IO getHandlerIO();

    /**
     * add listener for notification when it changed.
     */
    ISubscription addChangedListener(Runnable listener);
}
