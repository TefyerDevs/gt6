package com.gregtechceu.gt6.common.data;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.data.worldgen.modifier.BiomePlacement;
import com.gregtechceu.gt6.api.data.worldgen.modifier.DimensionFilter;
import com.gregtechceu.gt6.api.data.worldgen.modifier.FrequencyModifier;
import com.gregtechceu.gt6.common.worldgen.feature.FluidSproutFeature;
import com.gregtechceu.gt6.common.worldgen.feature.StoneBlobFeature;
import com.gregtechceu.gt6.common.worldgen.modifier.RubberTreeChancePlacement;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GTFeatures {

    public static final ResourceLocation NEW_ORE_VEIN_TOGGLE = Gregtech.id("vein_toggle");
    public static final ResourceLocation NEW_ORE_VEIN_RIDGED = Gregtech.id("vein_ridged");

    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(Registries.FEATURE,
            Gregtech.MOD_ID);

    public static final RegistryObject<StoneBlobFeature> STONE_BLOB = FEATURE_REGISTER.register("stone_blob",
            StoneBlobFeature::new);
    public static final RegistryObject<FluidSproutFeature> FLUID_SPROUT = FEATURE_REGISTER.register("fluid_sprout",
            FluidSproutFeature::new);

    public static void init() {
        Object inst = FrequencyModifier.FREQUENCY_MODIFIER; // seemingly useless access to init the class in time
        inst = DimensionFilter.DIMENSION_FILTER;
        inst = BiomePlacement.BIOME_PLACEMENT;
        inst = RubberTreeChancePlacement.RUBBER_TREE_CHANCE_PLACEMENT;
    }

    public static void init(IEventBus modEventBus) {
        FEATURE_REGISTER.register(modEventBus);
    }

    public static void register() {
        // no-op
    }
}
