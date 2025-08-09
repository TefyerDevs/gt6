package com.gregtechceu.gt6.common.data;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.GTAPI;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gt6.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gt6.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gt6.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gt6.common.fluid.potion.PotionFluid;
import com.gregtechceu.gt6.data.recipe.CustomTags;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;

import com.tterrag.registrate.util.entry.FluidEntry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static com.gregtechceu.gt6.common.registry.GTRegistration.REGISTRATE;

public class GTFluids {

    public static final FluidEntry<PotionFluid> POTION = REGISTRATE
            .fluid("potion", Gregtech.id("block/fluids/fluid.potion"), Gregtech.id("block/fluids/fluid.potion"),
                    PotionFluid.PotionFluidType::new, PotionFluid::new)
            .lang("Potion")
            .source(PotionFluid::new).noBlock().noBucket()
            .tag(CustomTags.POTION_FLUIDS)
            .register();

    public static void init() {
        // Register fluids for non-materials
        handleNonMaterialFluids(GTMaterials.Water, Fluids.WATER);
        handleNonMaterialFluids(GTMaterials.Lava, Fluids.LAVA);
        handleNonMaterialFluids(GTMaterials.Milk, ForgeMod.MILK);
        ForgeMod.enableMilkFluid();

        // register fluids for materials
        REGISTRATE.creativeModeTab(() -> GTCreativeModeTabs.MATERIAL_FLUID);
        for (MaterialRegistry registry : GTAPI.materialManager.getRegistries()) {
            GTRegistrate registrate = registry.getRegistrate();
            for (var material : registry.getAllMaterials()) {
                var fluidProperty = material.getProperty(PropertyKey.FLUID);

                if (fluidProperty != null) {
                    fluidProperty.registerFluids(material, registrate);
                }
            }
        }
    }

    public static void handleNonMaterialFluids(@NotNull Material material, @NotNull Fluid fluid) {
        var property = material.getProperty(PropertyKey.FLUID);
        property.getStorage().store(FluidStorageKeys.LIQUID, () -> fluid, null);
    }

    public static void handleNonMaterialFluids(@NotNull Material material, @NotNull Supplier<Fluid> fluid) {
        var property = material.getProperty(PropertyKey.FLUID);
        property.getStorage().store(FluidStorageKeys.LIQUID, fluid, null);
    }
}
