package com.gregtechceu.gt6.integration.rei;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.api.item.tool.GTToolType;
import com.gregtechceu.gt6.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gt6.api.registry.GTRegistries;
import com.gregtechceu.gt6.common.data.*;
import com.gregtechceu.gt6.common.data.machines.GTMultiMachines;
import com.gregtechceu.gt6.common.fluid.potion.PotionFluid;
import com.gregtechceu.gt6.common.fluid.potion.PotionFluidHelper;
import com.gregtechceu.gt6.config.ConfigHolder;
import com.gregtechceu.gt6.integration.rei.circuit.GTProgrammedCircuitCategory;
import com.gregtechceu.gt6.integration.rei.multipage.MultiblockInfoDisplayCategory;
import com.gregtechceu.gt6.integration.rei.oreprocessing.GTOreProcessingDisplayCategory;
import com.gregtechceu.gt6.integration.rei.orevein.GTBedrockFluidDisplayCategory;
import com.gregtechceu.gt6.integration.rei.orevein.GTBedrockOreDisplayCategory;
import com.gregtechceu.gt6.integration.rei.orevein.GTOreVeinDisplayCategory;
import com.gregtechceu.gt6.integration.rei.recipe.GTRecipeREICategory;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.CollapsibleEntryRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.FluidComparatorRegistry;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@REIPluginClient
public class GTREIPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        // Categories
        registry.add(new MultiblockInfoDisplayCategory());
        if (!ConfigHolder.INSTANCE.compat.hideOreProcessingDiagrams)
            registry.add(new GTOreProcessingDisplayCategory());
        registry.add(new GTOreVeinDisplayCategory());
        registry.add(new GTBedrockFluidDisplayCategory());
        if (ConfigHolder.INSTANCE.machines.doBedrockOres)
            registry.add(new GTBedrockOreDisplayCategory());
        for (GTRecipeCategory category : GTRegistries.RECIPE_CATEGORIES) {
            if (category.shouldRegisterDisplays()) {
                registry.add(new GTRecipeREICategory(category));
            }
        }
        registry.add(new GTProgrammedCircuitCategory());

        // Workstations
        GTRecipeREICategory.registerWorkStations(registry);
        if (!ConfigHolder.INSTANCE.compat.hideOreProcessingDiagrams)
            GTOreProcessingDisplayCategory.registerWorkstations(registry);
        GTOreVeinDisplayCategory.registerWorkstations(registry);
        GTBedrockFluidDisplayCategory.registerWorkstations(registry);
        if (ConfigHolder.INSTANCE.machines.doBedrockOres)
            GTBedrockOreDisplayCategory.registerWorkstations(registry);
        registry.addWorkstations(GTRecipeREICategory.CATEGORIES.apply(GTRecipeTypes.CHEMICAL_RECIPES.getCategory()),
                EntryStacks.of(GTMultiMachines.LARGE_CHEMICAL_REACTOR.asStack()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        GTRecipeREICategory.registerDisplays(registry);
        MultiblockInfoDisplayCategory.registerDisplays(registry);
        if (!ConfigHolder.INSTANCE.compat.hideOreProcessingDiagrams)
            GTOreProcessingDisplayCategory.registerDisplays(registry);
        GTOreVeinDisplayCategory.registerDisplays(registry);
        GTBedrockFluidDisplayCategory.registerDisplays(registry);
        if (ConfigHolder.INSTANCE.machines.doBedrockOres)
            GTBedrockOreDisplayCategory.registerDisplays(registry);
        registry.add(new GTProgrammedCircuitCategory.GTProgrammedCircuitDisplay());
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void registerCollapsibleEntries(CollapsibleEntryRegistry registry) {
        for (GTToolType toolType : GTToolType.getTypes().values()) {
            registry.group(Gregtech.id("tool/" + toolType.name),
                    Component.translatable("gt6.tool.class." + toolType.name),
                    EntryIngredients.ofItemTag(toolType.itemTags.get(0)));
            // EntryIngredients.ofItemStacks(GTItems.TOOL_ITEMS.column(toolType).values().stream().filter(Objects::nonNull).map(ItemProviderEntry::get).map(IGTTool::get).collect(Collectors.toSet()))
        }

        for (var cell : GTMaterialBlocks.MATERIAL_BLOCKS.columnMap().entrySet()) {
            var value = cell.getValue();
            if (value.size() <= 1) continue;

            var material = cell.getKey();
            List<ItemLike> items = new ArrayList<>();
            for (var t : value.entrySet()) {
                var prefix = t.getKey();
                if (prefix == TagPrefix.frameGt ||
                        prefix == TagPrefix.block ||
                        prefix == TagPrefix.rawOreBlock)
                    continue;

                items.add(t.getValue());
            }

            var name = material.getName();
            var label = toUpperAllWords(name.replace("_", " "));
            registry.group(Gregtech.id("ore/" + name), Component.translatable("tagprefix.stone", label),
                    EntryIngredients.ofItems(items));
        }

        List<EntryStack<dev.architectury.fluid.FluidStack>> stacks = new ArrayList<>(BuiltInRegistries.POTION.size());
        for (Potion potion : BuiltInRegistries.POTION) {
            FluidStack stack = PotionFluidHelper.getFluidFromPotion(potion, PotionFluidHelper.BOTTLE_AMOUNT);
            stacks.add(EntryStacks
                    .of(dev.architectury.fluid.FluidStack.create(stack.getFluid(), stack.getAmount(), stack.getTag())));
        }
        registry.group(Gregtech.id("potion_fluids"), Component.translatable("gt6.rei.group.potion_fluids"), stacks);
    }

    @Override
    public void registerItemComparators(ItemComparatorRegistry registry) {
        registry.registerNbt(GTItems.PROGRAMMED_CIRCUIT.asItem());
        registry.registerNbt(GTItems.TURBINE_ROTOR.asItem());
    }

    @Override
    public void registerFluidComparators(FluidComparatorRegistry registry) {
        PotionFluid potionFluid = GTFluids.POTION.get();
        registry.registerNbt(potionFluid.getSource());
        registry.registerNbt(potionFluid.getFlowing());
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        for (Potion potion : BuiltInRegistries.POTION) {
            FluidStack stack = PotionFluidHelper.getFluidFromPotion(potion, PotionFluidHelper.BOTTLE_AMOUNT);
            registry.addEntry(EntryStacks.of(
                    dev.architectury.fluid.FluidStack.create(stack.getFluid(), stack.getAmount(), stack.getTag())));
        }
    }

    private static String toUpperAllWords(String text) {
        StringBuilder result = new StringBuilder();
        result.append(text.substring(0, 1).toUpperCase(Locale.ROOT));
        for (int i = 1; i < text.length(); i++) {
            if (" ".equals(text.substring(i - 1, i)))
                result.append(text.substring(i, i + 1).toUpperCase(Locale.ROOT));
            else
                result.append(text.charAt(i));
        }
        return result.toString();
    }
}
