package com.gregtechceu.gt6.api;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.addon.AddonFinder;
import com.gregtechceu.gt6.api.addon.IGTAddon;
import com.gregtechceu.gt6.api.block.ICoilType;
import com.gregtechceu.gt6.api.block.IFilterType;
import com.gregtechceu.gt6.api.data.chemical.material.IMaterialRegistryManager;
import com.gregtechceu.gt6.api.machine.multiblock.IBatteryData;
import com.gregtechceu.gt6.api.registry.GTRegistry;
import com.gregtechceu.gt6.common.block.BatteryBlock;
import com.gregtechceu.gt6.common.block.CoilBlock;
import com.gregtechceu.gt6.config.ConfigHolder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.fml.event.IModBusEvent;

import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GTAPI {

    /** Will always be available */
    public static Gregtech instance;
    /** Will be available at the Construction stage */
    public static IMaterialRegistryManager materialManager;

    /** Will be available at the Pre-Initialization stage */
    @Getter
    private static boolean highTier;
    private static boolean highTierInitialized;

    public static final Map<ICoilType, Supplier<CoilBlock>> HEATING_COILS = new HashMap<>();
    public static final Map<IFilterType, Supplier<Block>> CLEANROOM_FILTERS = new HashMap<>();
    public static final Map<IBatteryData, Supplier<BatteryBlock>> PSS_BATTERIES = new HashMap<>();

    /**
     * Initializes High-Tier. Internal use only, do not attempt to call this.
     */
    @ApiStatus.Internal
    public static void initializeHighTier() {
        if (highTierInitialized) throw new IllegalStateException("High-Tier is already initialized.");
        highTier = ConfigHolder.INSTANCE.machines.highTierContent ||
                AddonFinder.getAddons().stream().anyMatch(IGTAddon::requiresHighTier) || Gregtech.isDev();
        highTierInitialized = true;

        if (isHighTier()) Gregtech.LOGGER.info("High-Tier is Enabled.");
        else Gregtech.LOGGER.info("High-Tier is Disabled.");
    }

    public static class RegisterEvent<K, V> extends GenericEvent<V> implements IModBusEvent {

        private final GTRegistry<K, V> registry;

        public RegisterEvent(GTRegistry<K, V> registry, Class<V> clazz) {
            super(clazz);
            this.registry = registry;
        }

        public void register(K key, V value) {
            if (registry != null) registry.register(key, value);
        }

        public static class RL<V> extends RegisterEvent<ResourceLocation, V> {

            public RL(GTRegistry<ResourceLocation, V> registry, Class<V> clazz) {
                super(registry, clazz);
            }
        }

        public static class String<V> extends RegisterEvent<java.lang.String, V> {

            public String(GTRegistry<java.lang.String, V> registry, Class<V> clazz) {
                super(registry, clazz);
            }
        }
    }
}
