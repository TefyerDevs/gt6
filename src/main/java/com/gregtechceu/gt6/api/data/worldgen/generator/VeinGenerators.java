package com.gregtechceu.gt6.api.data.worldgen.generator;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.addon.AddonFinder;
import com.gregtechceu.gt6.api.addon.IGTAddon;
import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.api.data.worldgen.WorldGeneratorUtils;
import com.gregtechceu.gt6.api.data.worldgen.generator.veins.*;

import net.minecraft.resources.ResourceLocation;

import com.mojang.serialization.Codec;

import java.util.function.Function;

@SuppressWarnings("unused")
public class VeinGenerators {

    public static final Codec<NoopVeinGenerator> NO_OP = register(Gregtech.id("no_op"), NoopVeinGenerator.CODEC,
            entry -> NoopVeinGenerator.INSTANCE);

    public static final Codec<StandardVeinGenerator> STANDARD = register(Gregtech.id("standard"),
            StandardVeinGenerator.CODEC, StandardVeinGenerator::new);
    public static final Codec<LayeredVeinGenerator> LAYER = register(Gregtech.id("layer"), LayeredVeinGenerator.CODEC,
            LayeredVeinGenerator::new);
    public static final Codec<GeodeVeinGenerator> GEODE = register(Gregtech.id("geode"), GeodeVeinGenerator.CODEC,
            GeodeVeinGenerator::new);
    public static final Codec<DikeVeinGenerator> DIKE = register(Gregtech.id("dike"), DikeVeinGenerator.CODEC,
            DikeVeinGenerator::new);
    public static final Codec<VeinedVeinGenerator> VEINED = register(Gregtech.id("veined"), VeinedVeinGenerator.CODEC,
            VeinedVeinGenerator::new);
    public static final Codec<ClassicVeinGenerator> CLASSIC = register(Gregtech.id("classic"), ClassicVeinGenerator.CODEC,
            ClassicVeinGenerator::new);
    public static final Codec<CuboidVeinGenerator> CUBOID = register(Gregtech.id("cuboid"), CuboidVeinGenerator.CODEC,
            CuboidVeinGenerator::new);

    public static <
            T extends VeinGenerator> Codec<T> register(ResourceLocation id, Codec<T> codec,
                                                       Function<GTOreDefinition, ? extends VeinGenerator> function) {
        WorldGeneratorUtils.VEIN_GENERATORS.put(id, codec);
        WorldGeneratorUtils.VEIN_GENERATOR_FUNCTIONS.put(id, function);
        return codec;
    }

    public static void registerAddonGenerators() {
        AddonFinder.getAddons().forEach(IGTAddon::registerVeinGenerators);
    }
}
