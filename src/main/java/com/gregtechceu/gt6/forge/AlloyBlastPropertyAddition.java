package com.gregtechceu.gt6.forge;

import com.gregtechceu.gt6.api.GTAPI;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gt6.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gt6.api.data.chemical.material.properties.AlloyBlastProperty;
import com.gregtechceu.gt6.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gt6.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gt6.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gt6.api.fluids.FluidBuilder;
import com.gregtechceu.gt6.api.fluids.FluidState;
import com.gregtechceu.gt6.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gt6.common.data.GTMaterials;
import com.gregtechceu.gt6.data.recipe.misc.alloyblast.CustomAlloyBlastRecipeProducer;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Listen to PostMaterialEvent instead of doing this directly because it's a lot cleaner this way.
 */
// @Mod.EventBusSubscriber(modid = GTCEu.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlloyBlastPropertyAddition {

    @SubscribeEvent
    public static void addAlloyBlastProperties(PostMaterialEvent event) {
        for (Material material : GTAPI.materialManager.getRegisteredMaterials()) {
            if (!material.hasFlag(MaterialFlags.DISABLE_ALLOY_PROPERTY)) {
                addAlloyBlastProperty(material);
            }
        }
        // Alloy Blast Overriding
        GTMaterials.NiobiumNitride.getProperty(PropertyKey.ALLOY_BLAST)
                .setRecipeProducer(new CustomAlloyBlastRecipeProducer(1, 11, -1));

        GTMaterials.IndiumTinBariumTitaniumCuprate.getProperty(PropertyKey.ALLOY_BLAST)
                .setRecipeProducer(new CustomAlloyBlastRecipeProducer(-1, -1, 16));
    }

    public static void addAlloyBlastProperty(@NotNull Material material) {
        final List<MaterialStack> components = material.getMaterialComponents();
        // ignore materials which are not alloys
        if (components.size() < 2) return;

        BlastProperty blastProperty = material.getProperty(PropertyKey.BLAST);
        if (blastProperty == null) return;

        if (!material.hasProperty(PropertyKey.FLUID)) return;

        // if there are more than 2 fluid-only components in the material, do not generate a hot fluid
        if (components.stream().filter(AlloyBlastPropertyAddition::isMaterialStackFluidOnly).limit(3).count() > 2) {
            return;
        }

        material.setProperty(PropertyKey.ALLOY_BLAST, new AlloyBlastProperty(material.getBlastTemperature()));
        material.getProperty(PropertyKey.FLUID).getStorage().enqueueRegistration(FluidStorageKeys.MOLTEN,
                new FluidBuilder().state(FluidState.LIQUID));
    }

    private static boolean isMaterialStackFluidOnly(@NotNull MaterialStack ms) {
        return !ms.material().hasProperty(PropertyKey.DUST) && ms.material().hasProperty(PropertyKey.FLUID);
    }
}
