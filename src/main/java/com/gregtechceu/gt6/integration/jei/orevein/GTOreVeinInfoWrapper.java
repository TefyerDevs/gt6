package com.gregtechceu.gt6.integration.jei.orevein;

import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreVeinWidget;

import com.lowdragmc.lowdraglib.jei.ModularWrapper;

public class GTOreVeinInfoWrapper extends ModularWrapper<GTOreVeinWidget> {

    public final GTOreDefinition oreDefinition;

    public GTOreVeinInfoWrapper(GTOreDefinition oreDefinition) {
        super(new GTOreVeinWidget(oreDefinition));
        this.oreDefinition = oreDefinition;
    }
}
