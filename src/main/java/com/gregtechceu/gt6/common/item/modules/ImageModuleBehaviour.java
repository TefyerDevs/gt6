package com.gregtechceu.gt6.common.item.modules;

import com.gregtechceu.gt6.api.gui.GuiTextures;
import com.gregtechceu.gt6.api.item.component.IMonitorModuleItem;
import com.gregtechceu.gt6.client.renderer.monitor.IMonitorRenderer;
import com.gregtechceu.gt6.client.renderer.monitor.MonitorImageRenderer;
import com.gregtechceu.gt6.common.machine.multiblock.electric.CentralMonitorMachine;
import com.gregtechceu.gt6.common.machine.multiblock.electric.monitor.MonitorGroup;
import com.gregtechceu.gt6.common.network.GTNetwork;
import com.gregtechceu.gt6.common.network.packets.SCPacketMonitorGroupNBTChange;

import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.TextFieldWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.world.item.ItemStack;

public class ImageModuleBehaviour implements IMonitorModuleItem {

    @Override
    public IMonitorRenderer getRenderer(ItemStack stack, CentralMonitorMachine machine, MonitorGroup group) {
        return new MonitorImageRenderer(stack.getOrCreateTag().getString("url"));
    }

    @Override
    public Widget createUIWidget(ItemStack stack, CentralMonitorMachine machine, MonitorGroup group) {
        WidgetGroup builder = new WidgetGroup();
        TextFieldWidget textField = new TextFieldWidget(0, 0, 100, 10, null, null);
        textField.setCurrentString(stack.getOrCreateTag().getString("url"));

        ButtonWidget saveButton = new ButtonWidget(-40, 22, 20, 20, click -> {
            if (!click.isRemote) return;

            stack.getOrCreateTag().putString("url", textField.getCurrentString());
            GTNetwork.sendToServer(new SCPacketMonitorGroupNBTChange(stack, group, machine));
        });
        saveButton.setButtonTexture(GuiTextures.BUTTON_CHECK);
        builder.addWidget(textField);
        builder.addWidget(saveButton);
        return builder;
    }
}
