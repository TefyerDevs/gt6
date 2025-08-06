package com.gregtechceu.gt6.integration.emi.orevein;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.api.data.worldgen.bedrockore.BedrockOreDefinition;
import com.gregtechceu.gt6.client.ClientProxy;
import com.gregtechceu.gt6.common.data.GTItems;
import com.gregtechceu.gt6.common.data.GTMaterials;

import net.minecraft.network.chat.Component;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;

public class GTBedrockOreEmiCategory extends EmiRecipeCategory {

    public static final GTBedrockOreEmiCategory CATEGORY = new GTBedrockOreEmiCategory();

    public GTBedrockOreEmiCategory() {
        super(GTCEu.id("bedrock_ore_diagram"),
                EmiStack.of(ChemicalHelper.get(TagPrefix.rawOre, GTMaterials.Tungstate)));
    }

    public static void registerDisplays(EmiRegistry registry) {
        for (BedrockOreDefinition fluid : ClientProxy.CLIENT_BEDROCK_ORE_VEINS.values()) {
            registry.addRecipe(new GTBedrockOre(fluid));
        }
    }

    public static void registerWorkStations(EmiRegistry registry) {
        registry.addWorkstation(CATEGORY, EmiStack.of(GTItems.PROSPECTOR_HV.asStack()));
        registry.addWorkstation(CATEGORY, EmiStack.of(GTItems.PROSPECTOR_LuV.asStack()));
    }

    @Override
    public Component getName() {
        return Component.translatable("gt6.jei.bedrock_ore_diagram");
    }
}
