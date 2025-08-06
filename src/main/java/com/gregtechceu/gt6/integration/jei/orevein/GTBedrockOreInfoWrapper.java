package com.gregtechceu.gt6.integration.jei.orevein;

import com.gregtechceu.gt6.api.data.worldgen.bedrockore.BedrockOreDefinition;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreVeinWidget;

import com.lowdragmc.lowdraglib.jei.ModularWrapper;

public class GTBedrockOreInfoWrapper extends ModularWrapper<GTOreVeinWidget> {

    public final BedrockOreDefinition bedrockOre;

    public GTBedrockOreInfoWrapper(BedrockOreDefinition bedrockOre) {
        super(new GTOreVeinWidget(bedrockOre));
        this.bedrockOre = bedrockOre;
    }
}
