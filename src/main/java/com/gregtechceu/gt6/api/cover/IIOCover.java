package com.gregtechceu.gt6.api.cover;

import com.gregtechceu.gt6.api.capability.recipe.IO;
import com.gregtechceu.gt6.common.cover.data.ManualIOMode;

public interface IIOCover {

    int getTransferRate();

    IO getIo();

    ManualIOMode getManualIOMode();
}
