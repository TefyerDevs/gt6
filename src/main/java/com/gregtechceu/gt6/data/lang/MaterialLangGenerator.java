package com.gregtechceu.gt6.data.lang;

import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.chemical.material.registry.MaterialRegistry;

import com.tterrag.registrate.providers.RegistrateLangProvider;

import static com.gregtechceu.gt6.utils.FormattingUtil.toEnglishName;

public class MaterialLangGenerator {

    public static void generate(RegistrateLangProvider provider, MaterialRegistry registry) {
        for (Material material : registry.getAllMaterials())
            provider.add(material.getUnlocalizedName(), toEnglishName(material.getName()));
    }
}
