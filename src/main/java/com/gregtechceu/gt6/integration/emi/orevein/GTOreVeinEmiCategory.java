package com.gregtechceu.gt6.integration.emi.orevein;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.client.ClientProxy;
import com.gregtechceu.gt6.common.data.GTItems;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;

public class GTOreVeinEmiCategory extends EmiRecipeCategory {

    public static final GTOreVeinEmiCategory CATEGORY = new GTOreVeinEmiCategory();

    public GTOreVeinEmiCategory() {
        super(Gregtech.id("ore_vein_diagram"), EmiStack.of(Items.RAW_IRON));
    }

    public static void registerDisplays(EmiRegistry registry) {
        for (GTOreDefinition oreDefinition : ClientProxy.CLIENT_ORE_VEINS.values()) {
            registry.addRecipe(new GTEmiOreVein(oreDefinition));
        }
    }

    public static void registerWorkStations(EmiRegistry registry) {
        registry.addWorkstation(CATEGORY, EmiStack.of(GTItems.PROSPECTOR_LV.asStack()));
        registry.addWorkstation(CATEGORY, EmiStack.of(GTItems.PROSPECTOR_HV.asStack()));
        registry.addWorkstation(CATEGORY, EmiStack.of(GTItems.PROSPECTOR_LuV.asStack()));
    }

    @Override
    public Component getName() {
        return Component.translatable("gt6.jei.ore_vein_diagram");
    }
}
