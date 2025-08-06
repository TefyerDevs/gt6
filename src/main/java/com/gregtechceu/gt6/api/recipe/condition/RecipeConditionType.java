package com.gregtechceu.gt6.api.recipe.condition;

import com.gregtechceu.gt6.api.recipe.RecipeCondition;

import com.mojang.serialization.Codec;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RecipeConditionType<T extends RecipeCondition> {

    public final ConditionFactory<T> factory;
    @Getter
    public final Codec<T> codec;

    @FunctionalInterface
    public interface ConditionFactory<T extends RecipeCondition> {

        T createDefault();
    }
}
