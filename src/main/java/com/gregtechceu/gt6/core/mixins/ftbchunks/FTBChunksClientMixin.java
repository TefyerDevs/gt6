package com.gregtechceu.gt6.core.mixins.ftbchunks;

import com.gregtechceu.gt6.integration.map.ftbchunks.veins.fluid.FluidVeinIcon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec3;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.MinimapBlurMode;
import dev.ftb.mods.ftbchunks.client.MinimapPosition;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(value = FTBChunksClient.class, remap = false)
public class FTBChunksClientMixin {

    @Unique
    private boolean gt6$iconCheck;
    @Unique
    private double gt6$d;
    @Unique
    private float gt6$minimapRotation;

    @Inject(method = "renderHud",
            at = @At(value = "INVOKE",
                     target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V",
                     shift = At.Shift.AFTER,
                     remap = true),
            slice = @Slice(from = @At(value = "INVOKE",
                                      target = "Ldev/ftb/mods/ftbchunks/api/client/icon/MapIcon;getPos(F)Lnet/minecraft/world/phys/Vec3;"),
                           to = @At(value = "INVOKE",
                                    target = "Ldev/ftb/mods/ftbchunks/api/client/icon/MapIcon;draw(Ldev/ftb/mods/ftbchunks/api/client/icon/MapType;Lnet/minecraft/client/gui/GuiGraphics;IIIIZI)V")))
    private void gt6$injectRenderHud(GuiGraphics graphics, float tickDelta, CallbackInfo ci, @Local MapIcon icon) {
        if (gt6$iconCheck) {
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(GL11.GL_GEQUAL);
            var poseStack = graphics.pose();
            poseStack.rotateAround(Axis.ZP.rotationDegrees(gt6$minimapRotation + 180f), 0.5f, 0.5f, 0);
            poseStack.scale(1.143f, 1.143f, 0);
        }
    }

    @Inject(method = "renderHud",
            at = @At(value = "INVOKE",
                     target = "Ldev/ftb/mods/ftbchunks/api/client/icon/MapIcon;isVisible(Ldev/ftb/mods/ftbchunks/api/client/icon/MapType;DZ)Z",
                     shift = At.Shift.AFTER),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void gt6$saveLocals(GuiGraphics graphics, float tickDelta, CallbackInfo ci, Minecraft mc,
                                  double playerX, double playerY, double playerZ, double guiScale,
                                  int scaledWidth, int scaledHeight, MapDimension dim, long now, float zoom0,
                                  float zoom, MinimapBlurMode blurMode, boolean minimapBlur, int filter, int cx,
                                  int cz, float scale, boolean rotationLocked, float minimapRotation, int size,
                                  double halfSizeD, float halfSizeF, MinimapPosition minimapPosition, int x,
                                  int y, int offsetX, int offsetY,
                                  MinimapPosition.MinimapOffsetConditional offsetConditional, float border,
                                  int alpha, Tesselator tessellator, BufferBuilder buffer, PoseStack poseStack,
                                  Matrix4f m, float halfSizeBorderF, float offX, float offZ, float zws,
                                  Iterator var47, MapIcon icon, Vec3 pos, double distance, double d) {
        gt6$iconCheck = icon instanceof FluidVeinIcon;
        gt6$d = d;
        gt6$minimapRotation = minimapRotation;
    }

    @ModifyVariable(method = "renderHud",
                    name = "d",
                    at = @At(value = "STORE"),
                    slice = @Slice(from = @At(value = "INVOKE",
                                              target = "Ldev/ftb/mods/ftbchunks/api/client/icon/MapIcon;isVisible(Ldev/ftb/mods/ftbchunks/api/client/icon/MapType;DZ)Z"),
                                   to = @At(value = "INVOKE",
                                            target = "Ldev/ftb/mods/ftbchunks/api/client/icon/MapIcon;getIconScale(Ldev/ftb/mods/ftbchunks/api/client/icon/MapType;)D")),
                    remap = false)
    private double gt6$valueLoad(double d) {
        if (gt6$iconCheck) {
            return gt6$d;
        }
        return d;
    }

    @Inject(method = "renderHud",
            at = @At(value = "INVOKE",
                     target = "Ldev/ftb/mods/ftbchunks/api/client/icon/MapIcon;draw(Ldev/ftb/mods/ftbchunks/api/client/icon/MapType;Lnet/minecraft/client/gui/GuiGraphics;IIIIZI)V",
                     shift = At.Shift.AFTER),
            remap = false)
    private void gt6$injectRenderHudPost(GuiGraphics graphics, float tickDelta, CallbackInfo ci,
                                           @Local MapIcon icon) {
        if (gt6$iconCheck) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthFunc(GL11.GL_LEQUAL);
        }
    }
}
