package com.gregtechceu.gt6.api.capability.energy.types.eu;

import com.gregtechceu.gt6.api.capability.energy.IEnergyBaseProvider;
import net.minecraft.core.Direction;

import java.math.BigInteger;

public interface IEnergyContainer extends IEnergyBaseProvider {

    @Override
    default EnergyInfo getEnergyInfo() {
        return new EnergyInfo(BigInteger.valueOf(getEnergyCapacity()), BigInteger.valueOf(getEnergyStored()));
    }

    @Override
    default boolean supportsBigIntEnergyValues() {
        return false;
    }

    /**
     * @return maximum amount of outputable energy packets per tick
     */
    default long getOutputAmperage() {
        return 0L;
    }

    /**
     * @return output energy packet size
     */
    default long getOutputVoltage() {
        return 0L;
    }

    /**
     * @return maximum amount of receivable energy packets per tick
     */
    long getInputAmperage();

    /**
     * @return output energy packet size
     *         Overflowing this value will explode machine.
     */
    long getInputVoltage();

    /**
     * @return input eu/s
     */
    @Override
    default long getInputPerSec() {
        return 0L;
    }

    /**
     * @return output eu/s
     */
    @Override
    default long getOutputPerSec() {
        return 0L;
    }

    IEnergyContainer DEFAULT = new IEnergyContainer() {

        @Override
        public long acceptEnergyFromNetwork(Direction Direction, long l, long l1) {
            return 0;
        }

        @Override
        public boolean inputsEnergy(Direction Direction) {
            return false;
        }

        @Override
        public long changeEnergy(long l) {
            return 0;
        }

        @Override
        public long getEnergyStored() {
            return 0;
        }

        @Override
        public long getEnergyCapacity() {
            return 0;
        }

        @Override
        public long getInputAmperage() {
            return 0;
        }

        @Override
        public long getInputVoltage() {
            return 0;
        }
    };
}
