package com.gregtechceu.gt6.utils;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RegistryUtil {

    private RegistryUtil() {}

    public static <T> List<ResourceKey<T>> resolveResourceKeys(ResourceKey<Registry<T>> registryKey,
                                                               String... locations) {
        return Arrays.stream(locations)
                .map(location -> ResourceKey.create(registryKey, new ResourceLocation(location)))
                .toList();
    }
}
