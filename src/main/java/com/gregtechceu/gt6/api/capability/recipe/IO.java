package com.gregtechceu.gt6.api.capability.recipe;

import com.gregtechceu.gt6.api.gui.widget.EnumSelectorWidget;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

import lombok.Getter;

/**
 * The capability can be input or output or both
 */
public enum IO implements EnumSelectorWidget.SelectableEnum {

    IN("gt6.io.import", "import"),
    OUT("gt6.io.export", "export"),
    BOTH("gt6.io.both", "both"),
    NONE("gt6.io.none", "none");

    @Getter
    public final String tooltip;
    @Getter
    public final IGuiTexture icon;

    IO(String tooltip, String textureName) {
        this.tooltip = tooltip;
        this.icon = new ResourceTexture("gt6:textures/gui/icon/io_mode/" + textureName + ".png");
    }

    public boolean support(IO io) {
        if (io == this) return true;
        if (io == NONE) return false;
        return this == BOTH;
    }
}
