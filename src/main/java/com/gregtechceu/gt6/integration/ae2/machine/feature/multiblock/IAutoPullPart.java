package com.gregtechceu.gt6.integration.ae2.machine.feature.multiblock;

import com.gregtechceu.gt6.api.gui.GuiTextures;
import com.gregtechceu.gt6.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gt6.api.gui.fancy.IFancyConfiguratorButton;
import com.gregtechceu.gt6.api.machine.feature.multiblock.IMultiPart;

import net.minecraft.network.chat.Component;

import appeng.api.stacks.GenericStack;

import java.util.List;
import java.util.function.Predicate;

public interface IAutoPullPart extends IMultiPart {

    boolean isAutoPull();

    void setAutoPull(boolean autoPull);

    void setAutoPullTest(Predicate<GenericStack> test);

    @Override
    default void attachConfigurators(ConfiguratorPanel configuratorPanel) {
        configuratorPanel.attachConfigurators(new IFancyConfiguratorButton.Toggle(
                GuiTextures.BUTTON_AUTO_PULL.getSubTexture(0, 0, 1, 0.5),
                GuiTextures.BUTTON_AUTO_PULL.getSubTexture(0, 0.5, 1, 0.5),
                this::isAutoPull,
                (clickData, pressed) -> setAutoPull(pressed))
                .setTooltipsSupplier(pressed -> List.of(Component.translatable("gt6.gui.me_bus.auto_pull_button"))));
    }
}
