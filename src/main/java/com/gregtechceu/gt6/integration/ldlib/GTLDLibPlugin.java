package com.gregtechceu.gt6.integration.ldlib;

import com.gregtechceu.gt6.common.data.GTSyncedFieldAccessors;

import com.lowdragmc.lowdraglib.plugin.ILDLibPlugin;
import com.lowdragmc.lowdraglib.plugin.LDLibPlugin;

@LDLibPlugin
public class GTLDLibPlugin implements ILDLibPlugin {

    @Override
    public void onLoad() {
        GTSyncedFieldAccessors.init();
    }
}
