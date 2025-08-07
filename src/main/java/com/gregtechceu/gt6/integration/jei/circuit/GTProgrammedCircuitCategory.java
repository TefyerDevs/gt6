package com.gregtechceu.gt6.integration.jei.circuit;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.common.data.GTItems;
import com.gregtechceu.gt6.integration.xei.widgets.GTProgrammedCircuitWidget;

import com.lowdragmc.lowdraglib.jei.ModularUIRecipeCategory;
import com.lowdragmc.lowdraglib.jei.ModularWrapper;

import net.minecraft.network.chat.Component;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import org.jetbrains.annotations.Nullable;

public class GTProgrammedCircuitCategory extends
                                         ModularUIRecipeCategory<GTProgrammedCircuitCategory.GTProgrammedCircuitWrapper> {

    public final static RecipeType<GTProgrammedCircuitWrapper> RECIPE_TYPE = new RecipeType<>(
            Gregtech.id("programmed_circuit"), GTProgrammedCircuitWrapper.class);

    private final IDrawable background;
    private final IDrawable icon;

    public GTProgrammedCircuitCategory(IJeiHelpers helpers) {
        background = helpers.getGuiHelper().createBlankDrawable(150, 80);
        icon = helpers.getGuiHelper().createDrawableItemStack(GTItems.PROGRAMMED_CIRCUIT.asStack());
    }

    @Override
    public RecipeType<GTProgrammedCircuitWrapper> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gt6.jei.programmed_circuit");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    public static class GTProgrammedCircuitWrapper extends ModularWrapper<GTProgrammedCircuitWidget> {

        public GTProgrammedCircuitWrapper() {
            super(new GTProgrammedCircuitWidget());
        }
    }
}
