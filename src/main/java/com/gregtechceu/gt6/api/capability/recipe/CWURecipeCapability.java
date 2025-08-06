package com.gregtechceu.gt6.api.capability.recipe;

import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.content.Content;
import com.gregtechceu.gt6.api.recipe.content.ContentModifier;
import com.gregtechceu.gt6.api.recipe.content.SerializerInteger;

import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

public class CWURecipeCapability extends RecipeCapability<Integer> {

    public final static CWURecipeCapability CAP = new CWURecipeCapability();

    protected CWURecipeCapability() {
        super("cwu", 0xFFEEEE00, false, 3, SerializerInteger.INSTANCE);
    }

    @Override
    public Integer copyInner(Integer content) {
        return content;
    }

    @Override
    public Integer copyWithModifier(Integer content, ContentModifier modifier) {
        return modifier.apply(content);
    }

    @Override
    public void addXEIInfo(WidgetGroup group, int xOffset, GTRecipe recipe, List<Content> contents, boolean perTick,
                           boolean isInput, MutableInt yOffset) {
        if (perTick) {
            int cwu = contents.stream().map(Content::getContent).mapToInt(CWURecipeCapability.CAP::of).sum();
            group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10),
                    LocalizationUtils.format("gt6.recipe.computation_per_tick", cwu)));
        }
        if (recipe.data.getBoolean("duration_is_total_cwu")) {
            group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10),
                    LocalizationUtils.format("gt6.recipe.total_computation", recipe.duration)));
        }
    }
}
