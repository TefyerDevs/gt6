package com.gregtechceu.gt6.api.capability.energy;

import net.minecraft.core.Direction;

import java.math.BigInteger;

public interface IEnergyBaseProvider {

    record EnergyInfo(BigInteger capacity, BigInteger stored) {}

    EnergyInfo getEnergyInfo();

    long getInputPerSec();

    long getOutputPerSec();

    boolean supportsBigIntEnergyValues();
    /**
     * @return if this container accepts energy from the given side
     */
    boolean inputsEnergy(Direction side);

    /**
     * @return if this container can output energy to the given side
     */
    default boolean outputsEnergy(Direction side) {
        return false;
    }


    /**
     * @return the maximum amount of energy that can be inserted
     */
    default long getEnergyCanBeInserted() {
        return getEnergyCapacity() - getEnergyStored();
    }

    /**
     * @return amount of currently stored energy
     */
    long getEnergyStored();

    /**
     * @return maximum amount of storable energy
     */
    long getEnergyCapacity();

    /**
     * This method is basically {@link #changeEnergy(long)}, but it also handles amperes.
     * This method should always be used when energy is passed between blocks.
     *
     * @param voltage  amount of energy packets (energy to add / input voltage)
     * @param amperage packet size (energy to add / input amperage)
     * @return amount of used amperes. 0 if not accepted anything.
     */
    long acceptEnergyFromNetwork(Direction side, long voltage, long amperage);
    /**
     * This changes the amount stored.
     * <b>This should only be used internally</b> (f.e. draining while working or filling while generating).
     * For transfer between blocks use {@link #acceptEnergyFromNetwork(Direction, long, long)}!!!
     *
     * @param differenceAmount amount of energy to add (>0) or remove (<0)
     * @return amount of energy added or removed
     */
    long changeEnergy(long differenceAmount);

    /**
     * Adds specified amount of energy to this energy container
     *
     * @param energyToAdd amount of energy to add
     * @return amount of energy added
     */
    default long addEnergy(long energyToAdd) {
        return changeEnergy(energyToAdd);
    }

    /**
     * Removes specified amount of energy from this energy container
     *
     * @param energyToRemove amount of energy to remove
     * @return amount of energy removed
     */
    default long removeEnergy(long energyToRemove) {
        return -changeEnergy(-energyToRemove);
    }

    /**
     * @return true if information like energy capacity should be hidden from TOP.
     *         Useful for cables
     */
    default boolean isOneProbeHidden() {
        return false;
    }
}
