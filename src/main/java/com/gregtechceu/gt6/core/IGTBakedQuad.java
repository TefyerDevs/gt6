package com.gregtechceu.gt6.core;

import net.minecraft.client.renderer.block.model.BakedQuad;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface IGTBakedQuad {

    @ApiStatus.Internal
    default BakedQuad gt6$setTextureKey(@Nullable String key) {
        return (BakedQuad) this;
    }

    default @Nullable String gt6$getTextureKey() {
        return null;
    }
}
