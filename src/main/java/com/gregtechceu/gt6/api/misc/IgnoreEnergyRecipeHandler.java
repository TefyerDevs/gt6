package com.gregtechceu.gt6.api.misc;

import com.gregtechceu.gt6.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gt6.api.capability.recipe.IO;
import com.gregtechceu.gt6.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gt6.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.ingredient.EnergyStack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IgnoreEnergyRecipeHandler implements IRecipeHandler<EnergyStack> {

    @Override
    public List<EnergyStack> handleRecipeInner(IO io, GTRecipe recipe, List<EnergyStack> left, boolean simulate) {
        return null;
    }

    @Override
    public @NotNull List<Object> getContents() {
        return List.of(EnergyStack.MAX);
    }

    @Override
    public double getTotalContentAmount() {
        return Long.MAX_VALUE;
    }

    @Override
    public RecipeCapability<EnergyStack> getCapability() {
        return EURecipeCapability.CAP;
    }
}
