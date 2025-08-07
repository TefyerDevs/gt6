package com.gregtechceu.gt6.integration.rei.orevein;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.client.ClientProxy;
import com.gregtechceu.gt6.common.data.GTItems;
import com.gregtechceu.gt6.integration.xei.widgets.GTOreVeinWidget;

import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.rei.IGui2Renderer;
import com.lowdragmc.lowdraglib.rei.ModularUIDisplayCategory;
import com.lowdragmc.lowdraglib.utils.Size;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import lombok.Getter;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import org.jetbrains.annotations.NotNull;

@Getter
public class GTOreVeinDisplayCategory extends ModularUIDisplayCategory<GTOreVeinDisplay> {

    public static final CategoryIdentifier<GTOreVeinDisplay> CATEGORY = CategoryIdentifier
            .of(Gregtech.id("ore_vein_diagram"));

    private final Renderer icon;

    private final Size size;

    public GTOreVeinDisplayCategory() {
        this.icon = IGui2Renderer.toDrawable(new ItemStackTexture(Items.IRON_INGOT.asItem()));
        this.size = new Size(10 + GTOreVeinWidget.width, 140);
    }

    @Override
    public CategoryIdentifier<? extends GTOreVeinDisplay> getCategoryIdentifier() {
        return CATEGORY;
    }

    @Override
    public int getDisplayHeight() {
        return getSize().height;
    }

    @Override
    public int getDisplayWidth(GTOreVeinDisplay display) {
        return getSize().width;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("gt6.jei.ore_vein_diagram");
    }

    public static void registerDisplays(DisplayRegistry registry) {
        for (GTOreDefinition oreDefinition : ClientProxy.CLIENT_ORE_VEINS.values()) {
            registry.add(new GTOreVeinDisplay(oreDefinition));
        }
    }

    public static void registerWorkstations(CategoryRegistry registry) {
        registry.addWorkstations(GTOreVeinDisplayCategory.CATEGORY, EntryStacks.of(GTItems.PROSPECTOR_LV.asStack()));
        registry.addWorkstations(GTOreVeinDisplayCategory.CATEGORY, EntryStacks.of(GTItems.PROSPECTOR_HV.asStack()));
        registry.addWorkstations(GTOreVeinDisplayCategory.CATEGORY, EntryStacks.of(GTItems.PROSPECTOR_LuV.asStack()));
    }
}
