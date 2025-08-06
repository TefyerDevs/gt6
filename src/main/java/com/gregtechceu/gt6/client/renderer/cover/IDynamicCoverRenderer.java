package com.gregtechceu.gt6.client.renderer.cover;

import com.gregtechceu.gt6.api.machine.MetaMachine;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IDynamicCoverRenderer {

    void render(MetaMachine machine, Direction face, float partialTick, PoseStack poseStack, MultiBufferSource buffer,
                int packedLight, int packedOverlay);
}
