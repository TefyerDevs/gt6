package com.gregtechceu.gt6.api.misc;

import com.gregtechceu.gt6.api.capability.energy.IEnergyBaseProvider;

import net.minecraft.MethodsReturnNonnullByDefault;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EnergyBaseProviderList implements IEnergyBaseProvider {

    private final List<? extends IEnergyBaseProvider> list;

    public EnergyBaseProviderList(List<? extends IEnergyBaseProvider> list) {
        this.list = list;
    }

    @Override
    public EnergyInfo getEnergyInfo() {
        BigInteger capacity = BigInteger.ZERO;
        BigInteger stored = BigInteger.ZERO;

        for (IEnergyBaseProvider energyInfoProvider : list) {
            EnergyInfo energyInfo = energyInfoProvider.getEnergyInfo();

            capacity = capacity.add(energyInfo.capacity());
            stored = stored.add(energyInfo.stored());
        }

        return new EnergyInfo(capacity, stored);
    }

    @Override
    public long getInputPerSec() {
        long sum = 0;
        for (IEnergyBaseProvider eip : list) {
            sum += eip.getInputPerSec();
        }
        return sum;
    }

    @Override
    public long getOutputPerSec() {
        long sum = 0;
        for (IEnergyBaseProvider eip : list) {
            sum += eip.getOutputPerSec();
        }
        return sum;
    }

    @Override
    public boolean supportsBigIntEnergyValues() {
        return list.size() > 1;
    }
}
