package com.gregtechceu.gt6.core.mixins.client;

import com.gregtechceu.gt6.core.IGTBakedQuad;

import net.minecraft.client.renderer.block.model.BakedQuad;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BakedQuad.class)
public class BakedQuadMixin implements IGTBakedQuad {

    @Unique
    private String gt6$textureKey = null;

    @Override
    public BakedQuad gt6$setTextureKey(@Nullable String key) {
        this.gt6$textureKey = key;
        return (BakedQuad) (Object) this;
    }

    @Override
    public String gt6$getTextureKey() {
        return gt6$textureKey;
    }
}
