package com.gregtechceu.gt6.integration.jei.recipe;

import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.integration.xei.widgets.GTRecipeWidget;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.jei.ModularWrapper;

public class GTRecipeWrapper extends ModularWrapper<Widget> {

    public final GTRecipe recipe;

    public GTRecipeWrapper(GTRecipe recipe) {
        super(new GTRecipeWidget(recipe));
        this.recipe = recipe;
    }
}
