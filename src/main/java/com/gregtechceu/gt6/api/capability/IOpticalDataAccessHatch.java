package com.gregtechceu.gt6.api.capability;

public interface IOpticalDataAccessHatch extends IDataAccessHatch {

    /**
     * @return if this hatch transmits data through cables
     */
    boolean isTransmitter();
}
