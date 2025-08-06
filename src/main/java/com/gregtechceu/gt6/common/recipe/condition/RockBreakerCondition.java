package com.gregtechceu.gt6.common.recipe.condition;

import com.gregtechceu.gt6.api.machine.trait.RecipeLogic;
import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.RecipeCondition;
import com.gregtechceu.gt6.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gt6.common.data.GTRecipeConditions;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class RockBreakerCondition extends RecipeCondition {

    public static final Codec<RockBreakerCondition> CODEC = RecordCodecBuilder
            .create(instance -> RecipeCondition.isReverse(instance)
                    .apply(instance, RockBreakerCondition::new));

    public final static RockBreakerCondition INSTANCE = new RockBreakerCondition();

    public RockBreakerCondition(boolean isReverse) {
        super(isReverse);
    }

    @Override
    public RecipeConditionType<?> getType() {
        return GTRecipeConditions.ROCK_BREAKER;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("recipe.condition.rock_breaker.tooltip");
    }

    @Override
    public boolean testCondition(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        var fluidA = BuiltInRegistries.FLUID.get(new ResourceLocation(recipe.data.getString("fluidA")));
        var fluidB = BuiltInRegistries.FLUID.get(new ResourceLocation(recipe.data.getString("fluidB")));
        boolean hasFluidA = false, hasFluidB = false;
        var level = recipeLogic.machine.self().getLevel();
        var pos = recipeLogic.machine.self().getPos();
        for (Direction side : GTUtil.DIRECTIONS) {
            if (side.getAxis() != Direction.Axis.Y) {
                var fluid = level.getFluidState(pos.relative(side));
                if (fluid.getType() == fluidA) hasFluidA = true;
                if (fluid.getType() == fluidB) hasFluidB = true;
                if (hasFluidA && hasFluidB) return true;
            }
        }
        return false;
    }

    @Override
    public RecipeCondition createTemplate() {
        return new RockBreakerCondition();
    }
}
