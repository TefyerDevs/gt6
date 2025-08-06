package com.gregtechceu.gt6.integration.jei.orevein;

import com.gregtechceu.gt6.api.data.worldgen.bedrockfluid.BedrockFluidDefinition;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreVeinWidget;

import com.lowdragmc.lowdraglib.jei.ModularWrapper;

public class GTBedrockFluidInfoWrapper extends ModularWrapper<GTOreVeinWidget> {

    public final BedrockFluidDefinition fluid;

    public GTBedrockFluidInfoWrapper(BedrockFluidDefinition fluid) {
        super(new GTOreVeinWidget(fluid));
        this.fluid = fluid;
    }
}
