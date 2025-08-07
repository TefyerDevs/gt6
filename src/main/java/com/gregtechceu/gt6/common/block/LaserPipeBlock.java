package com.gregtechceu.gt6.common.block;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.block.PipeBlock;
import com.gregtechceu.gt6.api.blockentity.PipeBlockEntity;
import com.gregtechceu.gt6.api.capability.forge.GTCapability;
import com.gregtechceu.gt6.api.pipenet.IPipeNode;
import com.gregtechceu.gt6.client.model.PipeModel;
import com.gregtechceu.gt6.client.renderer.block.PipeBlockRenderer;
import com.gregtechceu.gt6.common.blockentity.LaserPipeBlockEntity;
import com.gregtechceu.gt6.common.data.GTBlockEntities;
import com.gregtechceu.gt6.common.pipelike.laser.LaserPipeProperties;
import com.gregtechceu.gt6.common.pipelike.laser.LaserPipeType;
import com.gregtechceu.gt6.common.pipelike.laser.LevelLaserPipeNet;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LaserPipeBlock extends PipeBlock<LaserPipeType, LaserPipeProperties, LevelLaserPipeNet> {

    public final PipeBlockRenderer renderer;
    public final PipeModel model;
    private final LaserPipeProperties properties;

    public LaserPipeBlock(Properties properties, LaserPipeType type) {
        super(properties, type);
        this.properties = LaserPipeProperties.INSTANCE;
        this.model = new PipeModel(LaserPipeType.NORMAL.getThickness(), () -> Gregtech.id("block/pipe/pipe_laser_side"),
                () -> Gregtech.id("block/pipe/pipe_laser_in"), null, null);
        this.renderer = new PipeBlockRenderer(this.model);
    }

    @OnlyIn(Dist.CLIENT)
    public static BlockColor tintedColor() {
        return (blockState, level, blockPos, index) -> {
            if (blockPos != null && level != null &&
                    level.getBlockEntity(blockPos) instanceof PipeBlockEntity<?, ?> pipe) {
                if (!pipe.getFrameMaterial().isNull()) {
                    if (index == 3) {
                        return pipe.getFrameMaterial().getMaterialRGB();
                    } else if (index == 4) {
                        return pipe.getFrameMaterial().getMaterialSecondaryRGB();
                    }
                }
                if (pipe.isPainted()) {
                    return pipe.getRealColor();
                }
            }
            return -1;
        };
    }

    @Override
    public LevelLaserPipeNet getWorldPipeNet(ServerLevel world) {
        return LevelLaserPipeNet.getOrCreate(world);
    }

    @Override
    public BlockEntityType<? extends PipeBlockEntity<LaserPipeType, LaserPipeProperties>> getBlockEntityType() {
        return GTBlockEntities.LASER_PIPE.get();
    }

    @Override
    public LaserPipeProperties createRawData(BlockState pState, @Nullable ItemStack pStack) {
        return LaserPipeProperties.INSTANCE;
    }

    @Override
    public LaserPipeProperties createProperties(IPipeNode<LaserPipeType, LaserPipeProperties> pipeTile) {
        LaserPipeType pipeType = pipeTile.getPipeType();
        if (pipeType == null) return getFallbackType();
        return this.pipeType.modifyProperties(properties);
    }

    @Override
    public LaserPipeProperties getFallbackType() {
        return LaserPipeProperties.INSTANCE;
    }

    @Override
    public @Nullable PipeBlockRenderer getRenderer(BlockState state) {
        return renderer;
    }

    @Override
    protected PipeModel getPipeModel() {
        return model;
    }

    @Override
    public boolean canPipesConnect(IPipeNode<LaserPipeType, LaserPipeProperties> selfTile, Direction side,
                                   IPipeNode<LaserPipeType, LaserPipeProperties> sideTile) {
        return selfTile instanceof LaserPipeBlockEntity && sideTile instanceof LaserPipeBlockEntity;
    }

    @Override
    public boolean canPipeConnectToBlock(IPipeNode<LaserPipeType, LaserPipeProperties> selfTile, Direction side,
                                         @Nullable BlockEntity tile) {
        return tile != null && tile.getCapability(GTCapability.CAPABILITY_LASER, side.getOpposite()).isPresent();
    }
}
