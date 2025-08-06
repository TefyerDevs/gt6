package com.gregtechceu.gt6.common.block;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.block.MaterialPipeBlock;
import com.gregtechceu.gt6.api.blockentity.PipeBlockEntity;
import com.gregtechceu.gt6.api.capability.forge.GTCapability;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gt6.api.data.chemical.material.properties.WireProperties;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.api.pipenet.IPipeNode;
import com.gregtechceu.gt6.client.model.PipeModel;
import com.gregtechceu.gt6.common.blockentity.CableBlockEntity;
import com.gregtechceu.gt6.common.data.GTBlockEntities;
import com.gregtechceu.gt6.common.data.GTDamageTypes;
import com.gregtechceu.gt6.common.data.GTMaterialBlocks;
import com.gregtechceu.gt6.common.pipelike.cable.Insulation;
import com.gregtechceu.gt6.common.pipelike.cable.LevelEnergyNet;
import com.gregtechceu.gt6.utils.FormattingUtil;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CableBlock extends MaterialPipeBlock<Insulation, WireProperties, LevelEnergyNet> {

    public CableBlock(Properties properties, Insulation insulation, Material material) {
        super(properties, insulation, material);
    }

    @Override
    public int tinted(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index) {
        if (pipeType.isCable && index == 0) {
            return 0x404040;
        }
        return super.tinted(state, level, pos, index);
    }

    @Override
    protected WireProperties createProperties(Insulation insulation, Material material) {
        return insulation.modifyProperties(material.getProperty(PropertyKey.WIRE));
    }

    @Override
    protected WireProperties createMaterialData() {
        return material.getProperty(PropertyKey.WIRE);
    }

    @Override
    public LevelEnergyNet getWorldPipeNet(ServerLevel level) {
        return LevelEnergyNet.getOrCreate(level);
    }

    @Override
    public BlockEntityType<? extends PipeBlockEntity<Insulation, WireProperties>> getBlockEntityType() {
        return GTBlockEntities.CABLE.get();
    }

    @Override
    public boolean canPipesConnect(IPipeNode<Insulation, WireProperties> selfTile, Direction side,
                                   IPipeNode<Insulation, WireProperties> sideTile) {
        return selfTile instanceof CableBlockEntity && sideTile instanceof CableBlockEntity;
    }

    @Override
    public boolean canPipeConnectToBlock(IPipeNode<Insulation, WireProperties> selfTile, Direction side,
                                         @Nullable BlockEntity tile) {
        return tile != null &&
                tile.getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER, side.getOpposite()).isPresent();
    }

    @Override
    protected PipeModel createPipeModel() {
        return pipeType.createPipeModel(material);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        WireProperties wireProperties = createProperties(defaultBlockState(), stack);
        int tier = GTUtil.getTierByVoltage(wireProperties.getVoltage());
        if (wireProperties.isSuperconductor())
            tooltip.add(Component.translatable("gt6.cable.superconductor", GTValues.VN[tier]));
        tooltip.add(Component.translatable("gt6.cable.voltage",
                FormattingUtil.formatNumbers(wireProperties.getVoltage()), GTValues.VNF[tier]));
        tooltip.add(Component.translatable("gt6.cable.amperage",
                FormattingUtil.formatNumbers(wireProperties.getAmperage())));
        tooltip.add(Component.translatable("gt6.cable.loss_per_block",
                FormattingUtil.formatNumbers(wireProperties.getLossPerBlock())));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // dont apply damage if there is a frame box
        var pipeNode = getPipeTile(level, pos);
        if (pipeNode == null) {
            GTCEu.LOGGER.error("Pipe was null");
            return;
        }
        if (!pipeNode.getFrameMaterial().isNull()) {
            BlockState frameState = GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, pipeNode.getFrameMaterial())
                    .getDefaultState();
            frameState.getBlock().entityInside(frameState, level, pos, entity);
            return;
        }
        if (level.isClientSide) return;

        Insulation insulation = getPipeTile(level, pos).getPipeType();
        if (insulation.insulationLevel == -1 && entity instanceof LivingEntity entityLiving) {
            CableBlockEntity cable = (CableBlockEntity) getPipeTile(level, pos);
            if (cable != null && cable.getFrameMaterial().isNull() &&
                    cable.getNodeData().getLossPerBlock() > 0) {
                long voltage = cable.getCurrentMaxVoltage();
                double amperage = cable.getAverageAmperage();
                if (voltage > 0L && amperage > 0L) {
                    float damageAmount = (float) ((GTUtil.getTierByVoltage(voltage) + 1) * amperage * 4);
                    entityLiving.hurt(GTDamageTypes.ELECTRIC.source(level), damageAmount);
                    if (entityLiving instanceof ServerPlayer) {
                        // TODO advancments
                        // AdvancementTriggers.ELECTROCUTION_DEATH.trigger((ServerPlayer) entityLiving);
                    }
                }
            }
        }
    }
}
