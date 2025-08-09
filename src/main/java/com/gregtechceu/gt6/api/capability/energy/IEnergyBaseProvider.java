package com.gregtechceu.gt6.api.capability.energy;

import java.math.BigInteger;

public interface IEnergyBaseProvider {

    record EnergyInfo(BigInteger capacity, BigInteger stored) {}

    EnergyInfo getEnergyInfo();

    long getInputPerSec();

    long getOutputPerSec();

    boolean supportsBigIntEnergyValues();

    /**
     * @return true if information like energy capacity should be hidden from TOP.
     *         Useful for cables
     */
    default boolean isOneProbeHidden() {
        return false;
    }
}
