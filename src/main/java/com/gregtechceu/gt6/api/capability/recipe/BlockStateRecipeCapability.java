package com.gregtechceu.gt6.api.capability.recipe;

import com.gregtechceu.gt6.api.recipe.content.SerializerBlockState;

import net.minecraft.world.level.block.state.BlockState;

public class BlockStateRecipeCapability extends RecipeCapability<BlockState> {

    public final static BlockStateRecipeCapability CAP = new BlockStateRecipeCapability();

    protected BlockStateRecipeCapability() {
        super("block_state", 0xFFABABAB, false, 5, SerializerBlockState.INSTANCE);
    }

    @Override
    public BlockState copyInner(BlockState content) {
        return content;
    }
}
