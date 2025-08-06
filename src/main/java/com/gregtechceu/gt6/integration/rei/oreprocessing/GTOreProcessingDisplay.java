package com.gregtechceu.gt6.integration.rei.oreprocessing;

import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreByProductWidget;

import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.rei.ModularDisplay;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class GTOreProcessingDisplay extends ModularDisplay<WidgetGroup> {

    private final Material material;

    public GTOreProcessingDisplay(Material material) {
        super(() -> new GTOreByProductWidget(material), GTOreProcessingDisplayCategory.CATEGORY);
        this.material = material;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(material.getResourceLocation());
    }
}
