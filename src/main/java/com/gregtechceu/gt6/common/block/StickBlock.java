package com.gregtechceu.gt6.common.block;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.GTAPI;
import com.gregtechceu.gt6.api.item.SurfaceRockBlockItem;
import com.gregtechceu.gt6.client.renderer.block.StickBlockRenderer;
import com.gregtechceu.gt6.client.renderer.block.SurfaceRockRenderer;
import com.gregtechceu.gt6.integration.map.cache.server.ServerCache;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.gregtechceu.gt6.utils.EntityUtils.summonItemEntity;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class StickBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final VoxelShape AABB_NORTH = Block.box(2, 2, 0, 14, 14, 3);
    private static final VoxelShape AABB_SOUTH = Block.box(2, 2, 13, 14, 14, 16);
    private static final VoxelShape AABB_WEST = Block.box(0, 2, 2, 3, 14, 14);
    private static final VoxelShape AABB_EAST = Block.box(13, 2, 2, 16, 14, 14);
    private static final VoxelShape AABB_UP = Block.box(2, 13, 2, 14, 16, 14);
    private static final VoxelShape AABB_DOWN = Block.box(2, 0, 2, 14, 3, 14);

    public StickBlock(Properties properties) {
        super(properties);

        registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.DOWN));
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest,
                                       FluidState fluid) {
        summonItemEntity(level, pos, Items.STICK);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        if (level.destroyBlock(pos, true, player)) {
            summonItemEntity(level, pos, Items.STICK);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case DOWN -> AABB_DOWN;
            case UP -> AABB_UP;
            case NORTH -> AABB_NORTH;
            case SOUTH -> AABB_SOUTH;
            case WEST -> AABB_WEST;
            case EAST -> AABB_EAST;
        };
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter view, BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var facing = state.getValue(FACING);
        var attachedBlock = pos.relative(facing);

        return level.getBlockState(attachedBlock).isFaceSturdy(level, attachedBlock, facing.getOpposite());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos,
                                boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (!canSurvive(state, level, pos)) {
            summonItemEntity(level, pos, Items.STICK);
            Block.updateOrDestroy(state, Blocks.AIR.defaultBlockState(), level, pos, Block.UPDATE_ALL);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateForDirection(context.getNearestLookingVerticalDirection());
    }

    public BlockState getStateForDirection(Direction direction) {
        return defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public String getDescriptionId() {
        return "block.stick";
    }

    @Override
    public MutableComponent getName() {
        return Component.translatable("block.stick");
    }
}
