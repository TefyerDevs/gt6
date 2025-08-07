package com.gregtechceu.gt6.integration.jei.oreprocessing;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.GTCEuAPI;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gt6.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gt6.api.data.chemical.material.properties.PropertyKey;

import com.lowdragmc.lowdraglib.jei.ModularUIRecipeCategory;

import net.minecraft.network.chat.Component;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import org.jetbrains.annotations.NotNull;

import static com.gregtechceu.gt6.api.data.tag.TagPrefix.rawOre;
import static com.gregtechceu.gt6.common.data.GTMachines.*;
import static com.gregtechceu.gt6.common.data.GTMaterials.Iron;

public class GTOreProcessingInfoCategory extends ModularUIRecipeCategory<GTOreProcessingInfoWrapper> {

    public final static RecipeType<GTOreProcessingInfoWrapper> RECIPE_TYPE = new RecipeType<>(
            Gregtech.id("ore_processing_diagram"), GTOreProcessingInfoWrapper.class);
    private final IDrawable background;
    private final IDrawable icon;

    public GTOreProcessingInfoCategory(IJeiHelpers helpers) {
        IGuiHelper guiHelper = helpers.getGuiHelper();
        this.background = guiHelper.createBlankDrawable(186, 174);
        this.icon = helpers.getGuiHelper().createDrawableItemStack(ChemicalHelper.get(rawOre, Iron));
    }

    public static void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(RECIPE_TYPE, GTCEuAPI.materialManager.getRegisteredMaterials().stream()
                .filter((material) -> material.hasProperty(PropertyKey.ORE) &&
                        !material.hasFlag(MaterialFlags.NO_ORE_PROCESSING_TAB))
                .map(GTOreProcessingInfoWrapper::new)
                .toList());
    }

    public static void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(MACERATOR[GTValues.LV].asStack(), RECIPE_TYPE);
        registration.addRecipeCatalyst(ORE_WASHER[GTValues.LV].asStack(), RECIPE_TYPE);
        registration.addRecipeCatalyst(THERMAL_CENTRIFUGE[GTValues.LV].asStack(), RECIPE_TYPE);
        registration.addRecipeCatalyst(CENTRIFUGE[GTValues.LV].asStack(), RECIPE_TYPE);
        registration.addRecipeCatalyst(CHEMICAL_BATH[GTValues.LV].asStack(), RECIPE_TYPE);
        registration.addRecipeCatalyst(ELECTROMAGNETIC_SEPARATOR[GTValues.LV].asStack(), RECIPE_TYPE);
        registration.addRecipeCatalyst(SIFTER[GTValues.LV].asStack(), RECIPE_TYPE);
    }

    @Override
    @NotNull
    public RecipeType<GTOreProcessingInfoWrapper> getRecipeType() {
        return RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("gt6.jei.ore_processing_diagram");
    }

    @NotNull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return icon;
    }
}
