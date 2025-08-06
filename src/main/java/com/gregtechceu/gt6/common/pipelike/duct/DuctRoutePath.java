package com.gregtechceu.gt6.common.pipelike.duct;

import com.gregtechceu.gt6.api.capability.GTCapabilityHelper;
import com.gregtechceu.gt6.api.capability.IHazardParticleContainer;
import com.gregtechceu.gt6.api.pipenet.IRoutePath;
import com.gregtechceu.gt6.common.blockentity.DuctPipeBlockEntity;
import com.gregtechceu.gt6.utils.FacingPos;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DuctRoutePath implements IRoutePath<IHazardParticleContainer> {

    @Getter
    private final DuctPipeBlockEntity targetPipe;
    @NotNull
    @Getter
    private final Direction targetFacing;
    @Getter
    private final int distance;
    @Getter
    private final DuctPipeProperties properties;

    public DuctRoutePath(DuctPipeBlockEntity targetPipe, @NotNull Direction facing, int distance,
                         DuctPipeProperties properties) {
        this.targetPipe = targetPipe;
        this.targetFacing = facing;
        this.distance = distance;
        this.properties = properties;
    }

    @Override
    public @NotNull BlockPos getTargetPipePos() {
        return targetPipe.getPipePos();
    }

    @Override
    public @Nullable IHazardParticleContainer getHandler(Level world) {
        return GTCapabilityHelper.getHazardContainer(world, getTargetPipePos().relative(targetFacing),
                targetFacing.getOpposite());
    }

    public FacingPos toFacingPos() {
        return new FacingPos(getTargetPipePos(), targetFacing);
    }
}
