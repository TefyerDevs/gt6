package com.gregtechceu.gt6.integration.kjs.builders;

import com.gregtechceu.gt6.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gt6.api.registry.registrate.BuilderBase;

import net.minecraft.resources.ResourceLocation;

public class MaterialIconTypeBuilder extends BuilderBase<MaterialIconType> {

    public MaterialIconTypeBuilder(ResourceLocation id) {
        super(id);
    }

    @Override
    public MaterialIconType register() {
        return new MaterialIconType(this.id.getPath());
    }
}
