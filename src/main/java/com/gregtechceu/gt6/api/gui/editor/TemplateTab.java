package com.gregtechceu.gt6.api.gui.editor;

import com.lowdragmc.lowdraglib.gui.editor.annotation.LDLRegister;
import com.lowdragmc.lowdraglib.gui.editor.ui.menu.MenuTab;
import com.lowdragmc.lowdraglib.gui.util.TreeBuilder;

@LDLRegister(name = "template_tab", group = "editor.gt6")
public class TemplateTab extends MenuTab {

    protected TreeBuilder.Menu createMenu() {
        return TreeBuilder.Menu.start();
    }
}
