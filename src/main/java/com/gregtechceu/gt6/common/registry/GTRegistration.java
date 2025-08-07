package com.gregtechceu.gt6.common.registry;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.registry.registrate.GTRegistrate;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class GTRegistration {

    public static final GTRegistrate REGISTRATE = GTRegistrate.create(Gregtech.MOD_ID);
    static {
        GTRegistration.REGISTRATE.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    }

    private GTRegistration() {/**/}
}
