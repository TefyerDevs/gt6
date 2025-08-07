package com.gregtechceu.gt6.api.data.worldgen.generator;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.addon.AddonFinder;
import com.gregtechceu.gt6.api.addon.IGTAddon;
import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.api.data.worldgen.WorldGeneratorUtils;
import com.gregtechceu.gt6.api.data.worldgen.generator.indicators.NoopIndicatorGenerator;
import com.gregtechceu.gt6.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator;

import net.minecraft.resources.ResourceLocation;

import com.mojang.serialization.Codec;

import java.util.function.Function;

public class IndicatorGenerators {

    public static final Codec<NoopIndicatorGenerator> NO_OP = register(Gregtech.id("no_op"), NoopIndicatorGenerator.CODEC,
            entry -> NoopIndicatorGenerator.INSTANCE);

    public static final Codec<SurfaceIndicatorGenerator> SURFACE = register(Gregtech.id("surface"),
            SurfaceIndicatorGenerator.CODEC, SurfaceIndicatorGenerator::new);

    public static <T extends IndicatorGenerator> Codec<T> register(ResourceLocation id, Codec<T> codec,
                                                                   Function<GTOreDefinition, T> function) {
        WorldGeneratorUtils.INDICATOR_GENERATORS.put(id, codec);
        WorldGeneratorUtils.INDICATOR_GENERATOR_FUNCTIONS.put(id, function);
        return codec;
    }

    public static void registerAddonGenerators() {
        AddonFinder.getAddons().forEach(IGTAddon::registerIndicatorGenerators);
    }
}
