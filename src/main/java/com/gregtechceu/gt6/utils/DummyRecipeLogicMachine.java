package com.gregtechceu.gt6.utils;

import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.WorkableTieredMachine;
import com.gregtechceu.gt6.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gt6.api.machine.trait.RecipeHandlerList;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;

import java.util.Collection;

/**
 * Dummy machine used for searching recipes outside of a machine.
 */
public class DummyRecipeLogicMachine extends WorkableTieredMachine implements IRecipeLogicMachine {

    public DummyRecipeLogicMachine(IMachineBlockEntity be, int tier, Int2IntFunction tankScalingFunction,
                                   Collection<RecipeHandlerList> handlers,
                                   Object... args) {
        super(be, tier, tankScalingFunction, args);
        reinitializeHandlers(handlers);
    }

    public void reinitializeHandlers(Collection<RecipeHandlerList> handlers) {
        this.capabilitiesProxy.clear();
        this.capabilitiesFlat.clear();
        for (RecipeHandlerList handlerList : handlers) {
            this.addHandlerList(handlerList);
        }
    }
}
