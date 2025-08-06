package com.gregtechceu.gt6.api.recipe.lookup.ingredient;

import com.gregtechceu.gt6.common.data.GTRecipeCapabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

@FunctionalInterface
public interface MapIngredientFunction<T> {

    /**
     * Convert the passed object to a list of recipe lookup filters.
     *
     * @param ingredient The ingredient to convert.
     *                   e.g. {@link Ingredient} or {@link ItemStack} for {@link GTRecipeCapabilities#ITEM}
     * @return a list of recipe lookup filters.
     */
    List<AbstractMapIngredient> getIngredients(T ingredient);
}
