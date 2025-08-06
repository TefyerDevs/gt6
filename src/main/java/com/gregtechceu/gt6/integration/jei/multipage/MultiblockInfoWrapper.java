package com.gregtechceu.gt6.integration.jei.multipage;

import com.gregtechceu.gt6.api.gui.widget.PatternPreviewWidget;
import com.gregtechceu.gt6.api.machine.MultiblockMachineDefinition;

import com.lowdragmc.lowdraglib.jei.ModularWrapper;

public class MultiblockInfoWrapper extends ModularWrapper<PatternPreviewWidget> {

    public final MultiblockMachineDefinition definition;

    public MultiblockInfoWrapper(MultiblockMachineDefinition definition) {
        super(PatternPreviewWidget.getPatternWidget(definition));
        this.definition = definition;
    }
}
