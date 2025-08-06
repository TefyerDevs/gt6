package com.gregtechceu.gt6.api.item.component;

import com.gregtechceu.gt6.client.renderer.monitor.IMonitorRenderer;
import com.gregtechceu.gt6.common.machine.multiblock.electric.CentralMonitorMachine;
import com.gregtechceu.gt6.common.machine.multiblock.electric.monitor.MonitorGroup;

import com.lowdragmc.lowdraglib.gui.widget.Widget;

import net.minecraft.world.item.ItemStack;

public interface IMonitorModuleItem extends IItemComponent {

    default void tick(ItemStack stack, CentralMonitorMachine machine, MonitorGroup group) {}

    IMonitorRenderer getRenderer(ItemStack stack, CentralMonitorMachine machine, MonitorGroup group);

    Widget createUIWidget(ItemStack stack, CentralMonitorMachine machine, MonitorGroup group);
}
