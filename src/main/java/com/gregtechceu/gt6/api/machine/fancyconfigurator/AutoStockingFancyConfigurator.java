package com.gregtechceu.gt6.api.machine.fancyconfigurator;

import com.gregtechceu.gt6.api.gui.fancy.IFancyConfigurator;
import com.gregtechceu.gt6.api.gui.widget.IntInputWidget;
import com.gregtechceu.gt6.common.data.GTItems;
import com.gregtechceu.gt6.config.ConfigHolder;
import com.gregtechceu.gt6.integration.ae2.machine.MEStockingBusPartMachine;
import com.gregtechceu.gt6.integration.ae2.machine.feature.multiblock.IMEStockingPart;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.network.chat.Component;

public class AutoStockingFancyConfigurator implements IFancyConfigurator {

    private IMEStockingPart machine;

    public AutoStockingFancyConfigurator(IMEStockingPart machine) {
        this.machine = machine;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gt6.gui.adv_stocking_config.title");
    }

    @Override
    public IGuiTexture getIcon() {
        return new ItemStackTexture(GTItems.TOOL_DATA_STICK.asStack());
    }

    @Override
    public Widget createConfigurator() {
        var group = new WidgetGroup(0, 0, 90, 70);

        String suffix = machine instanceof MEStockingBusPartMachine ? "min_item_count" : "min_fluid_count";

        group.addWidget(new LabelWidget(4, 2, "gt6.gui.title.adv_stocking_config." + suffix));
        group.addWidget(new IntInputWidget(4, 12, 81, 14, machine::getMinStackSize,
                machine::setMinStackSize).setMin(1)
                .appendHoverTooltips(Component.translatable("gt6.gui.adv_stocking_config." + suffix)));
        group.addWidget(new LabelWidget(4, 36, "gt6.gui.title.adv_stocking_config.ticks_per_cycle"));
        group.addWidget(new IntInputWidget(4, 46, 81, 14, machine::getTicksPerCycle,
                machine::setTicksPerCycle).setMin(ConfigHolder.INSTANCE.compat.ae2.updateIntervals)
                .setHoverTooltips(Component.translatable("gt6.gui.adv_stocking_config.ticks_per_cycle")));

        return group;
    }
}
