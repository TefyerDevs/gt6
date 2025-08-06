package com.gregtechceu.gt6.api.machine.feature;

import com.gregtechceu.gt6.api.capability.recipe.RecipeCapability;

import net.minecraft.util.StringRepresentable;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.annotations.NotNull;

public interface IVoidable extends IMachineFeature {

    default boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
        return self().getDefinition().getRecipeOutputLimits().containsKey(capability);
    }

    default Object2IntMap<RecipeCapability<?>> getOutputLimits() {
        return self().getDefinition().getRecipeOutputLimits();
    }

    enum VoidingMode implements StringRepresentable {

        VOID_NONE("gt6.gui.multiblock_no_voiding"),
        VOID_ITEMS("gt6.gui.multiblock_item_voiding"),
        VOID_FLUIDS("gt6.gui.multiblock_fluid_voiding"),
        VOID_BOTH("gt6.gui.multiblock_item_fluid_voiding");

        public static final VoidingMode[] VALUES = values();

        public final String localeName;

        VoidingMode(String name) {
            this.localeName = name;
        }

        @NotNull
        @Override
        public String getSerializedName() {
            return localeName;
        }
    }
}
