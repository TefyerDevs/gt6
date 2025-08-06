package com.gregtechceu.gt6.data.tags;

import com.gregtechceu.gt6.common.data.GTMaterials;
import com.gregtechceu.gt6.data.recipe.CustomTags;

import net.minecraft.world.level.material.Fluid;

import com.tterrag.registrate.providers.RegistrateTagsProvider;

public class FluidTagLoader {

    public static void init(RegistrateTagsProvider.IntrinsicImpl<Fluid> provider) {
        provider.addTag(CustomTags.LIGHTER_FLUIDS).add(GTMaterials.Butane.getFluid(), GTMaterials.Propane.getFluid());
    }
}
