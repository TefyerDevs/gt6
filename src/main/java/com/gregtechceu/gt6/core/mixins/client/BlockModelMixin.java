package com.gregtechceu.gt6.core.mixins.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockModel;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockModel.class, priority = 1500)
public class BlockModelMixin {

    @ModifyReturnValue(method = "bakeFace", at = @At(value = "RETURN"))
    private static BakedQuad gt6$addQuadTextureKeyBlock(BakedQuad quad, BlockElement part, BlockElementFace face) {
        return quad.gt6$setTextureKey(face.texture);
    }
}
