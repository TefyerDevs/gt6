package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.MetaMachine;
import com.gregtechceu.gt6.common.machine.multiblock.primitive.PrimitivePumpMachine;
import com.gregtechceu.gt6.utils.FormattingUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class PrimitivePumpBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof IMachineBlockEntity blockEntity) {
            MetaMachine machine = blockEntity.getMetaMachine();
            if (machine instanceof PrimitivePumpMachine pump) {
                long water = blockAccessor.getServerData().getLong("waterProduced");
                iTooltip.add(Component.translatable("gt6.top.primitive_pump_production",
                        FormattingUtil.formatNumbers(water)));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof IMachineBlockEntity blockEntity) {
            MetaMachine machine = blockEntity.getMetaMachine();
            if (machine instanceof PrimitivePumpMachine pump) {
                compoundTag.putLong("waterProduced", pump.getFluidProduction());
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return GTCEu.id("primitive_pump");
    }
}
