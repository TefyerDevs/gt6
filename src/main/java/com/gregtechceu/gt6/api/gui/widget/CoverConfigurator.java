package com.gregtechceu.gt6.api.gui.widget;

import com.gregtechceu.gt6.api.capability.ICoverable;
import com.gregtechceu.gt6.api.cover.CoverBehavior;
import com.gregtechceu.gt6.api.cover.IUICover;
import com.gregtechceu.gt6.api.gui.GuiTextures;
import com.gregtechceu.gt6.api.gui.fancy.IFancyConfigurator;
import com.gregtechceu.gt6.common.data.GTItems;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class CoverConfigurator implements IFancyConfigurator {

    protected final ICoverable coverable;
    // runtime
    @Nullable
    protected final Direction side;
    @Nullable
    protected final CoverBehavior coverBehavior;

    @Override
    public Component getTitle() {
        return Component.translatable("gt6.gui.cover_setting.title");
    }

    @Override
    public IGuiTexture getIcon() {
        return new ItemStackTexture(GTItems.ITEM_FILTER.get());
    }

    @Override
    public Widget createConfigurator() {
        WidgetGroup group = new WidgetGroup(new Position(0, 0));
        if (side != null) {
            if (coverable.getCoverAtSide(side) instanceof IUICover iuiCover) {
                Widget coverConfigurator = iuiCover.createUIWidget();
                coverConfigurator.setBackground(GuiTextures.BACKGROUND);
                coverConfigurator.setSelfPosition(new Position(4, -4));
                group.addWidget(coverConfigurator);
                group.setSize(new Size(Math.max(120, coverConfigurator.getSize().width + 8),
                        Math.max(80, 80 + coverConfigurator.getSize().height)));
            }
        }
        return group;
    }
}
