package com.gregtechceu.gt6.common.cover.data;

import com.gregtechceu.gt6.api.gui.widget.EnumSelectorWidget;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

public enum ManualIOMode implements EnumSelectorWidget.SelectableEnum {

    DISABLED("disabled"),
    FILTERED("filtered"),
    UNFILTERED("unfiltered");

    public static final ManualIOMode[] VALUES = values();

    public final String localeName;

    ManualIOMode(String localeName) {
        this.localeName = localeName;
    }

    @Override
    public String getTooltip() {
        return "cover.universal.manual_import_export.mode." + localeName;
    }

    @Override
    public IGuiTexture getIcon() {
        return new ResourceTexture("gt6:textures/gui/icon/manual_io_mode/" + localeName + ".png");
    }
}
