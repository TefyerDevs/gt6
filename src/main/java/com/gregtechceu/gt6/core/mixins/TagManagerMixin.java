package com.gregtechceu.gt6.core.mixins;

import com.gregtechceu.gt6.core.IGTTagLoader;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagManager;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.concurrent.Executor;

@Mixin(TagManager.class)
public class TagManagerMixin {

    @ModifyExpressionValue(method = "createLoader", at = @At(value = "NEW", target = "net/minecraft/tags/TagLoader"))
    private <T> TagLoader<Holder<T>> gt6$saveTagLoaderRegistry(TagLoader<Holder<T>> loader,
                                                                 ResourceManager rm, Executor executor,
                                                                 RegistryAccess.RegistryEntry<T> entry) {
        ((IGTTagLoader) loader).gt6$setRegistry(entry.value());
        return loader;
    }
}
