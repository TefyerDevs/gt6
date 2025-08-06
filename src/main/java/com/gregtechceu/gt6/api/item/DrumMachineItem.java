package com.gregtechceu.gt6.api.item;

import com.gregtechceu.gt6.api.block.IMachineBlock;
import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.chemical.material.properties.FluidPipeProperties;
import com.gregtechceu.gt6.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gt6.api.misc.forge.ThermalFluidHandlerItemStack;
import com.gregtechceu.gt6.common.data.GTMaterials;
import com.gregtechceu.gt6.common.data.machines.GTMachineUtils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DrumMachineItem extends MetaMachineItem {

    @NotNull
    private Material mat = GTMaterials.NULL;

    protected DrumMachineItem(IMachineBlock block, Properties properties, @NotNull Material mat) {
        super(block, properties);
        this.mat = mat;
    }

    public static DrumMachineItem create(IMachineBlock block, Properties properties, @NotNull Material mat) {
        return new DrumMachineItem(block, properties, mat);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        final FluidPipeProperties property = mat.getProperty(PropertyKey.FLUID_PIPE);
        if (property == null) {
            return null;
        }
        return new ThermalFluidHandlerItemStack(stack,
                GTMachineUtils.DRUM_CAPACITY.getInt(getDefinition()),
                property.getMaxFluidTemperature(), property.isGasProof(), property.isAcidProof(),
                property.isCryoProof(), property.isPlasmaProof());
    }
}
