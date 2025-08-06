package com.gregtechceu.gt6.core.mixins;

import com.gregtechceu.gt6.core.IGTTagLoader;
import com.gregtechceu.gt6.core.MixinHelpers;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(value = TagLoader.class, priority = 500)
public class TagLoaderMixin implements IGTTagLoader {

    @Nullable
    @Unique
    private Registry<?> gt6$storedRegistry;

    @Inject(method = "load", at = @At(value = "RETURN"))
    public void gt6$load(ResourceManager resourceManager,
                           CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir) {
        if (gt6$storedRegistry == null) return;
        MixinHelpers.generateGTDynamicTags(cir.getReturnValue(), gt6$storedRegistry);
    }

    @Override
    public void gt6$setRegistry(Registry<?> registry) {
        this.gt6$storedRegistry = registry;
    }
}
