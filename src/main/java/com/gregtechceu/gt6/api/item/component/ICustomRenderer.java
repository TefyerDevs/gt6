package com.gregtechceu.gt6.api.item.component;

import com.lowdragmc.lowdraglib.client.renderer.IRenderer;

import org.jetbrains.annotations.NotNull;

public interface ICustomRenderer extends IItemComponent {

    @NotNull
    IRenderer getRenderer();
}
