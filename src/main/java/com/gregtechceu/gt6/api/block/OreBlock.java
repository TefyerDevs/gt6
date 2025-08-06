package com.gregtechceu.gt6.api.block;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.client.renderer.block.OreBlockRenderer;
import com.gregtechceu.gt6.config.ConfigHolder;
import com.gregtechceu.gt6.integration.map.cache.server.ServerCache;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class OreBlock extends MaterialBlock {

    public OreBlock(Properties properties, TagPrefix tagPrefix, Material material) {
        super(properties, tagPrefix, material, false);
        if (GTCEu.isClientSide()) {
            OreBlockRenderer.create(this);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        if (!level.isClientSide) {
            ServerCache.instance.prospectByOreMaterial(
                    level.dimension(),
                    this.material,
                    pos,
                    (ServerPlayer) player,
                    ConfigHolder.INSTANCE.compat.minimap.oreBlockProspectRange);
        }
        return InteractionResult.PASS;
    }
}
