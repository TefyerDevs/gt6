package com.gregtechceu.gt6.client.renderer.block;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.common.data.GTBlocks;
import com.gregtechceu.gt6.data.pack.GTDynamicResourcePack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class StickBlockRenderer {
    public static void reinitModels() {
        ResourceLocation blockId = GTBlocks.STICK_BLOCK.getId();
        ResourceLocation modelId = blockId.withPrefix("block/");

        GTDynamicResourcePack.addBlockModel(blockId, new DelegatedModel(Gregtech.id("block/stick")));
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator
                .multiVariant(GTBlocks.STICK_BLOCK.get(), Variant.variant().with(VariantProperties.MODEL, modelId))
                .with(PropertyDispatch.property(BlockStateProperties.FACING)
                        .select(Direction.DOWN, Variant.variant())
                        .select(Direction.UP,
                                Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH,
                                Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH,
                                Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.WEST,
                                Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST,
                                Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))));
        GTDynamicResourcePack.addItemModel(blockId, new DelegatedModel(modelId));
    }

}
