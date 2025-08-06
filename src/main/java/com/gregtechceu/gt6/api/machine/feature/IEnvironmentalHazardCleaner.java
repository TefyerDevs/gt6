package com.gregtechceu.gt6.api.machine.feature;

import com.gregtechceu.gt6.api.data.medicalcondition.MedicalCondition;

public interface IEnvironmentalHazardCleaner extends IMachineFeature {

    float getRemovedLastSecond();

    void cleanHazard(MedicalCondition condition, float amount);
}
