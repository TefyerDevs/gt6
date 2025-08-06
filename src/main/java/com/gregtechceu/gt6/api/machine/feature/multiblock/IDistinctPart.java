package com.gregtechceu.gt6.api.machine.feature.multiblock;

import com.gregtechceu.gt6.api.gui.GuiTextures;
import com.gregtechceu.gt6.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gt6.api.gui.fancy.IFancyConfiguratorButton;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public interface IDistinctPart extends IMultiPart {

    boolean isDistinct();

    void setDistinct(boolean isDistinct);

    @Override
    default void attachConfigurators(ConfiguratorPanel configuratorPanel) {
        superAttachConfigurators(configuratorPanel);
        configuratorPanel.attachConfigurators(new IFancyConfiguratorButton.Toggle(
                GuiTextures.BUTTON_DISTINCT_BUSES.getSubTexture(0, 0.5, 1, 0.5),
                GuiTextures.BUTTON_DISTINCT_BUSES.getSubTexture(0, 0, 1, 0.5),
                this::isDistinct, (clickData, pressed) -> setDistinct(pressed))
                .setTooltipsSupplier(pressed -> List.of(
                        Component.translatable("gt6.multiblock.universal.distinct")
                                .setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
                                .append(Component.translatable(pressed ? "gt6.multiblock.universal.distinct.yes" :
                                        "gt6.multiblock.universal.distinct.no")))));
    }

    default void superAttachConfigurators(ConfiguratorPanel configuratorPanel) {
        IMultiPart.super.attachConfigurators(configuratorPanel);
    }
}
