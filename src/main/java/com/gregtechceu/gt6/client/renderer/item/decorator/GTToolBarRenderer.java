package com.gregtechceu.gt6.client.renderer.item.decorator;

import com.gregtechceu.gt6.api.item.IGTTool;
import com.gregtechceu.gt6.client.renderer.item.ToolChargeBarRenderer;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

import org.jetbrains.annotations.NotNull;

public class GTToolBarRenderer implements IItemDecorator {

    public static final GTToolBarRenderer INSTANCE = new GTToolBarRenderer();

    @Override
    public boolean render(@NotNull GuiGraphics guiGraphics, @NotNull Font font, ItemStack stack, int x, int y) {
        if (stack.getItem() instanceof IGTTool gtTool) {
            ToolChargeBarRenderer.renderBarsTool(guiGraphics, gtTool, stack, x, y);
            return true;
        }
        return true;
    }
}
