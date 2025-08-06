package com.gregtechceu.gt6.integration.kjs;

import com.gregtechceu.gt6.integration.kjs.events.GTBedrockOreVeinEventJS;
import com.gregtechceu.gt6.integration.kjs.events.GTFluidVeinEventJS;
import com.gregtechceu.gt6.integration.kjs.events.GTOreVeinEventJS;
import com.gregtechceu.gt6.integration.kjs.events.RegisterCapesEventJS;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface GTCEuServerEvents {

    EventGroup GROUP = EventGroup.of("GTCEuServerEvents");

    EventHandler ORE_VEIN_MODIFICATION = GROUP.server("oreVeins", () -> GTOreVeinEventJS.class);
    EventHandler BEDROCK_ORE_VEIN_MODIFICATION = GROUP.server("bedrockOreVeins", () -> GTBedrockOreVeinEventJS.class);
    EventHandler FLUID_VEIN_MODIFICATION = GROUP.server("fluidVeins", () -> GTFluidVeinEventJS.class);

    EventHandler REGISTER_CAPES = GROUP.server("registerCapes", () -> RegisterCapesEventJS.class);
}
