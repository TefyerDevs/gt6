package com.gregtechceu.gt6.api.capability.energy.types.hu;

import com.gregtechceu.gt6.api.capability.energy.IEnergyBaseProvider;
import com.gregtechceu.gt6.api.capability.energy.types.eu.IEnergyContainer;
import net.minecraft.core.Direction;

import java.math.BigInteger;

public interface IHeatContainer extends IEnergyBaseProvider {

    @Override
    default EnergyInfo getEnergyInfo() {
        return new EnergyInfo(BigInteger.valueOf(getEnergyCapacity()), BigInteger.valueOf(getEnergyStored()));
    }

    @Override
    default boolean supportsBigIntEnergyValues() {
        return false;
    }

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

    /**
    *  @return output heat packet size
    *         Overflowing this value will explode machine.
    */
    long getInputHeat();

    /**
     * @return maximum amount of receivable heat packets per tick
     * */

    long getInputJoule();

    IHeatContainer DEFAULT = new IHeatContainer() {

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
        public long getInputJoule() {
            return 0;
        }

        @Override
        public long getInputHeat() {
            return 0;
        }
    };
}
