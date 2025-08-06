package com.gregtechceu.gt6.api.item;

import com.gregtechceu.gt6.api.block.IMachineBlock;
import com.gregtechceu.gt6.api.misc.forge.QuantumFluidHandlerItemStack;
import com.gregtechceu.gt6.common.machine.storage.QuantumTankMachine;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

public class QuantumTankMachineItem extends MetaMachineItem {

    public QuantumTankMachineItem(IMachineBlock block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new QuantumFluidHandlerItemStack(stack, QuantumTankMachine.TANK_CAPACITY.getLong(getDefinition()));
    }
}
