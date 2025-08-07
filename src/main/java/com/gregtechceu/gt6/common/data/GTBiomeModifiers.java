package com.gregtechceu.gt6.common.data;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.data.recipe.CustomTags;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class GTBiomeModifiers {

    public static final ResourceKey<BiomeModifier> RUBBER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            Gregtech.id("rubber_tree"));
    public static final ResourceKey<BiomeModifier> STONE_BLOB = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            Gregtech.id("stone_blob"));
    public static final ResourceKey<BiomeModifier> RAW_OIL_SPROUT = ResourceKey
            .create(ForgeRegistries.Keys.BIOME_MODIFIERS, Gregtech.id("raw_oil_sprout"));

    public static void bootstrap(BootstapContext<BiomeModifier> ctx) {
        HolderGetter<Biome> biomeLookup = ctx.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureRegistry = ctx.lookup(Registries.PLACED_FEATURE);

        HolderSet<Biome> biomes = biomeLookup.getOrThrow(CustomTags.HAS_RUBBER_TREE);
        Holder<PlacedFeature> rubberTree = placedFeatureRegistry.getOrThrow(GTPlacements.RUBBER_CHECKED);
        ctx.register(RUBBER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes,
                HolderSet.direct(rubberTree),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        HolderSet<Biome> overworld = biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD);
        Holder<PlacedFeature> redGraniteBlob = placedFeatureRegistry.getOrThrow(GTPlacements.RED_GRANITE_BLOB);
        Holder<PlacedFeature> marbleBlob = placedFeatureRegistry.getOrThrow(GTPlacements.MARBLE_BLOB);
        ctx.register(STONE_BLOB, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                overworld,
                HolderSet.direct(redGraniteBlob, marbleBlob),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        Holder<PlacedFeature> rawOilSprout = placedFeatureRegistry.getOrThrow(GTPlacements.RAW_OIL_SPROUT);
        ctx.register(RAW_OIL_SPROUT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                overworld,
                HolderSet.direct(rawOilSprout),
                GenerationStep.Decoration.FLUID_SPRINGS));
    }
}
