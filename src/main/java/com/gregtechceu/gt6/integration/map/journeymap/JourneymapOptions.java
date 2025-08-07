package com.gregtechceu.gt6.integration.map.journeymap;

import com.gregtechceu.gt6.Gregtech;

import journeymap.client.api.IClientAPI;
import journeymap.client.api.option.BooleanOption;
import journeymap.client.api.option.OptionCategory;

import java.util.HashMap;
import java.util.Map;

public class JourneymapOptions {

    private final Map<String, BooleanOption> layerOptions = new HashMap<>();

    public JourneymapOptions() {
        final String prefix = "gt6.journeymap.options.layers.";
        final OptionCategory category = new OptionCategory(Gregtech.MOD_ID, "gt6.journeymap.options.layers");

        final BooleanOption oreLayer = new BooleanOption(category, "ore_veins", prefix + "ore_veins", false);
        layerOptions.put(oreLayer.getFieldName(), oreLayer);
        final BooleanOption fluidLayer = new BooleanOption(category, "bedrock_fluids", prefix + "bedrock_fluids",
                false);
        layerOptions.put(fluidLayer.getFieldName(), fluidLayer);
    }

    public boolean showLayer(String name) {
        return layerOptions.get(name).get();
    }

    public void toggleLayer(String name, boolean active) {
        layerOptions.get(name).set(active);
        if (!active) {
            JourneymapRenderer.getMarkers().forEach((id, marker) -> {
                if (id.split("@")[0].equals(name)) {
                    IClientAPI api = JourneyMapPlugin.getJmApi();
                    api.remove(marker);
                }
            });
        } else {
            JourneymapRenderer.getMarkers().forEach((id, marker) -> {
                if (id.split("@")[0].equals(name)) {
                    try {
                        IClientAPI api = JourneyMapPlugin.getJmApi();
                        api.show(marker);
                    } catch (Exception e) {
                        // It never actually throws anything...
                        Gregtech.LOGGER.error("Failed to enable marker with name {}", name, e);
                    }
                }
            });
        }
    }
}
