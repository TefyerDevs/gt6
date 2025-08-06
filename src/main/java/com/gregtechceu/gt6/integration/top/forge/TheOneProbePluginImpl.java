package com.gregtechceu.gt6.integration.top.forge;

import com.gregtechceu.gt6.integration.top.TheOneProbePlugin;

import mcjty.theoneprobe.TheOneProbe;

public class TheOneProbePluginImpl {

    public static void init() {
        TheOneProbePlugin.init(TheOneProbe.theOneProbeImp);
    }
}
