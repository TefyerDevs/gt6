package com.gregtechceu.gt6.integration.emi.orevein;

import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.client.ClientProxy;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreVeinWidget;

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.resources.ResourceLocation;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GTEmiOreVein extends ModularEmiRecipe<WidgetGroup> {

    private final GTOreDefinition oreDefinition;

    public GTEmiOreVein(GTOreDefinition oreDefinition) {
        super(() -> new GTOreVeinWidget(oreDefinition));
        this.oreDefinition = oreDefinition;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return GTOreVeinEmiCategory.CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return ClientProxy.CLIENT_ORE_VEINS.inverse().get(oreDefinition).withPrefix("/ore_vein_diagram/");
    }

    @Override
    public List<EmiStack> getOutputs() {
        return GTOreVeinWidget.getContainedOresAndBlocks(oreDefinition)
                .stream()
                .map(EmiStack::of)
                .toList();
    }
}
