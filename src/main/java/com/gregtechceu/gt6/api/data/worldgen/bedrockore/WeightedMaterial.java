package com.gregtechceu.gt6.api.data.worldgen.bedrockore;

import com.gregtechceu.gt6.api.GTAPI;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.utils.WeightedEntry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record WeightedMaterial(Material material, int weight) implements WeightedEntry {

    public static final Codec<WeightedMaterial> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    GTAPI.materialManager.codec().fieldOf("material").forGetter(WeightedMaterial::material),
                    Codec.INT.fieldOf("weight").forGetter(WeightedMaterial::weight))
                    .apply(instance, WeightedMaterial::new));
}
