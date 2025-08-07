package com.gregtechceu.gt6.integration.map.journeymap;

import com.gregtechceu.gt6.Gregtech;

import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.event.ClientEvent;
import journeymap.client.api.event.RegistryEvent;
import lombok.Getter;

import java.util.EnumSet;

@ClientPlugin
public class JourneyMapPlugin implements IClientPlugin {

    @Getter
    private static boolean active = false;

    @Getter
    private static IClientAPI jmApi;

    @Getter
    private static JourneymapOptions options;

    @Override
    public void initialize(IClientAPI jmClientApi) {
        active = true;
        jmApi = jmClientApi;
        jmClientApi.subscribe(Gregtech.MOD_ID, EnumSet.of(ClientEvent.Type.REGISTRY));
        JourneymapEventListener.init();
    }

    @Override
    public String getModId() {
        return Gregtech.MOD_ID;
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event.type == ClientEvent.Type.REGISTRY) {
            RegistryEvent registryEvent = (RegistryEvent) event;
            if (registryEvent.getRegistryType() == RegistryEvent.RegistryType.OPTIONS) {
                options = new JourneymapOptions();
            }
        }
    }
}
