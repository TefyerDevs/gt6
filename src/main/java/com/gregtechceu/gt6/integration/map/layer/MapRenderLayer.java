package com.gregtechceu.gt6.integration.map.layer;

import com.gregtechceu.gt6.integration.map.GenericMapRenderer;

public abstract class MapRenderLayer {

    protected final String key;
    protected final GenericMapRenderer renderer;

    public MapRenderLayer(String key, GenericMapRenderer renderer) {
        this.key = key;
        this.renderer = renderer;
    }
}
