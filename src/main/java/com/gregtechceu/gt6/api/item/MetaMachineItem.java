package com.gregtechceu.gt6.api.item;

import com.gregtechceu.gt6.api.block.IMachineBlock;
import com.gregtechceu.gt6.api.block.PipeBlock;
import com.gregtechceu.gt6.api.machine.MachineDefinition;
import com.gregtechceu.gt6.api.pipenet.IPipeNode;
import com.gregtechceu.gt6.client.renderer.ItemWithBERModelRenderer;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MetaMachineItem extends BlockItem {

    public MetaMachineItem(IMachineBlock block, Properties properties) {
        super(block.self(), properties);
    }

    public MachineDefinition getDefinition() {
        return ((IMachineBlock) getBlock()).getDefinition();
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction side = context.getClickedFace();

        boolean superVal = super.placeBlock(context, state);

        if (!level.isClientSide) {
            BlockPos possiblePipe = pos.offset(side.getOpposite().getNormal());
            Block block = level.getBlockState(possiblePipe).getBlock();
            if (block instanceof PipeBlock<?, ?, ?>) {
                IPipeNode pipeTile = ((PipeBlock<?, ?, ?>) block).getPipeTile(level, possiblePipe);
                if (pipeTile != null && ((PipeBlock<?, ?, ?>) block).canPipeConnectToBlock(pipeTile, side.getOpposite(),
                        level.getBlockEntity(pos))) {
                    pipeTile.setConnection(side, true, false);
                }
            }
        }
        return superVal;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ItemWithBERModelRenderer.INSTANCE;
            }
        });
    }
}
