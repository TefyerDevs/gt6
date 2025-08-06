package com.gregtechceu.gt6.integration.jei.oreprocessing;

import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreByProductWidget;

import com.lowdragmc.lowdraglib.jei.ModularWrapper;

public class GTOreProcessingInfoWrapper extends ModularWrapper<GTOreByProductWidget> {

    public final Material material;

    public GTOreProcessingInfoWrapper(Material mat) {
        super(new GTOreByProductWidget(mat));
        this.material = mat;
    }
}
