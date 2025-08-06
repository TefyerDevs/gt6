package com.gregtechceu.gt6.integration.emi.circuit;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.common.data.GTItems;
import com.gregtechceu.gt6.common.item.IntCircuitBehaviour;
import com.gregtechceu.gt6.integration.xei.widgets.GTProgrammedCircuitWidget;

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

public class GTProgrammedCircuitCategory extends EmiRecipeCategory {

    public static final GTProgrammedCircuitCategory CATEGORY = new GTProgrammedCircuitCategory();

    public GTProgrammedCircuitCategory() {
        super(GTCEu.id("programmed_circuit"), EmiStack.of(GTItems.PROGRAMMED_CIRCUIT.asItem()));
    }

    public static void registerDisplays(EmiRegistry registry) {
        registry.addRecipe(new GTProgrammedCircuitCategory.GTProgrammedCircuitWrapper());
    }

    @Override
    public Component getName() {
        return Component.translatable("gt6.jei.programmed_circuit");
    }

    public static class GTProgrammedCircuitWrapper extends ModularEmiRecipe<GTProgrammedCircuitWidget> {

        public GTProgrammedCircuitWrapper() {
            super(GTProgrammedCircuitWidget::new);
        }

        @Override
        public EmiRecipeCategory getCategory() {
            return CATEGORY;
        }

        @Override
        public int getDisplayWidth() {
            return super.getDisplayWidth();
        }

        @Override
        public @Nullable ResourceLocation getId() {
            return GTCEu.id("programmed_circuit");
        }

        @Override
        public List<EmiStack> getOutputs() {
            return IntStream.range(0, 33)
                    .mapToObj(IntCircuitBehaviour::stack)
                    .map(EmiStack::of)
                    .toList();
        }

        @Override
        public boolean supportsRecipeTree() {
            return false;
        }

        @Override
        public boolean hideCraftable() {
            return true;
        }
    }
}
