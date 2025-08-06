package com.gregtechceu.gt6.api.fluids;

import com.gregtechceu.gt6.data.recipe.CustomTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum FluidState {

    LIQUID("gt6.fluid.state_liquid", CustomTags.LIQUID_FLUIDS),
    GAS("gt6.fluid.state_gas", Tags.Fluids.GASEOUS),
    PLASMA("gt6.fluid.state_plasma", CustomTags.PLASMA_FLUIDS),
    ;

    @Getter
    private final String translationKey;
    @Getter
    private final TagKey<Fluid> tagKey;

    FluidState(@NotNull String translationKey, @NotNull TagKey<Fluid> tagKey) {
        this.translationKey = translationKey;
        this.tagKey = tagKey;
    }
}
