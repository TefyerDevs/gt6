package com.gregtechceu.gtceu.api.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;
import com.gregtechceu.gtceu.api.recipe.chance.boost.ChanceBoostFunction;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerGroupDistinctness.BUS_DISTINCT;
import static com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerGroupDistinctness.BYPASS_DISTINCT;
import static com.gregtechceu.gtceu.api.recipe.RecipeHelper.addToRecipeHandlerMap;

public class RecipeRunner {

    private final GTRecipe recipe;
    private final IO io;
    private final boolean isTick;
    private final Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches;
    private final Map<IO, List<RecipeHandlerList>> capabilityProxies;
    private final boolean simulated;
    private Map<RecipeCapability<?>, List<Object>> recipeContents;
    private final Map<RecipeCapability<?>, List<Object>> searchRecipeContents;

    public RecipeRunner(GTRecipe recipe, IO io, boolean isTick,
                        IRecipeCapabilityHolder holder, Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches,
                        boolean simulated) {
        this.recipe = recipe;
        this.io = io;
        this.isTick = isTick;
        this.chanceCaches = chanceCaches;
        this.capabilityProxies = holder.getCapabilitiesProxy();
        this.recipeContents = new Reference2ObjectOpenHashMap<>();
        this.searchRecipeContents = simulated ? recipeContents : new Reference2ObjectOpenHashMap<>();
        this.simulated = simulated;
    }

    @NotNull
    public ActionResult handle(Map<RecipeCapability<?>, List<Content>> entries) {
        fillContentMatchList(entries);

        if (searchRecipeContents.isEmpty()) {
            return ActionResult.PASS_NO_CONTENTS;
        }

        return this.handleContents();
    }

    /**
     * Populates the content match list to know if conditions are satisfied.
     */
    private void fillContentMatchList(Map<RecipeCapability<?>, List<Content>> entries) {
        ChanceBoostFunction function = recipe.getType().getChanceFunction();
        int recipeTier = RecipeHelper.getPreOCRecipeEuTier(recipe);
        int chanceTier = recipeTier + recipe.ocLevel;
        for (var entry : entries.entrySet()) {
            RecipeCapability<?> cap = entry.getKey();
            if (!cap.doMatchInRecipe()) continue;

            ChanceLogic logic = recipe.getChanceLogicForCapability(cap, this.io, this.isTick);
            List<Content> chancedContents = new ArrayList<>();
            // skip if empty
            if (entry.getValue().isEmpty()) continue;
            // populate recipe content capability map
            var contentList = this.recipeContents.computeIfAbsent(cap, c -> new ArrayList<>());
            var searchContentList = this.searchRecipeContents.computeIfAbsent(cap, c -> new ArrayList<>());
            for (Content cont : entry.getValue()) {
                searchContentList.add(cont.content);

                // When simulating the recipe handling (used for recipe matching),
                // searchRecipeContents == recipeContents, so all contents, chanced and unchanced, must match
                if (simulated) continue;

                if (cont.chance >= cont.maxChance) {
                    contentList.add(cont.content);
                } else {
                    chancedContents.add(cont);
                }
            }

            // add chanced contents to the recipe content map
            if (!chancedContents.isEmpty()) {
                var cache = this.chanceCaches.get(cap);
                chancedContents = logic.roll(chancedContents, function, recipeTier, chanceTier, cache,
                        recipe.parallels * recipe.batchParallels);

                for (Content cont : chancedContents) {
                    contentList.add(cont.content);
                }
            }

            if (contentList.isEmpty()) recipeContents.remove(cap);
        }
    }

    private ActionResult handleContents() {
        if (recipeContents.isEmpty()) return ActionResult.SUCCESS;
        if (!capabilityProxies.containsKey(io)) {
            return ActionResult.FAIL_NO_CAPABILITIES;
        }

        List<RecipeHandlerList> handlers = capabilityProxies.getOrDefault(io, Collections.emptyList());
        // Only sort for non-tick outputs
        if (!isTick && io.support(IO.OUT)) {
            handlers.sort(RecipeHandlerList.COMPARATOR.reversed());
        }

        Map<RecipeHandlerGroup, List<RecipeHandlerList>> handlerGroups = new HashMap<>();
        for (var handler : handlers) {
            addToRecipeHandlerMap(handler.getGroup(), handler, handlerGroups);
        }
        // Specifically check distinct handlers first
        for (RecipeHandlerList handler : handlerGroups.getOrDefault(BUS_DISTINCT, Collections.emptyList())) {
            // Handle the contents of this handler and also all the bypassed handlers
            var res = handler.handleRecipe(io, recipe, searchRecipeContents, true);
            if (!res.isEmpty()) {
                for (RecipeHandlerList bypassHandler : handlerGroups.getOrDefault(BYPASS_DISTINCT,
                        Collections.emptyList())) {
                    res = bypassHandler.handleRecipe(io, recipe, res, true);
                    if (res.isEmpty()) break;
                }
            }
            if (res.isEmpty()) {
                if (!simulated) {
                    // Actually consume the contents of this handler and also all the bypassed handlers
                    recipeContents = handler.handleRecipe(io, recipe, recipeContents, false);
                    if (!recipeContents.isEmpty()) {
                        for (RecipeHandlerList bypassHandler : handlerGroups.getOrDefault(BYPASS_DISTINCT,
                                Collections.emptyList())) {
                            recipeContents = bypassHandler.handleRecipe(io, recipe, recipeContents, false);
                            if (recipeContents.isEmpty()) break;
                        }
                    }
                }
                recipeContents.clear();
                return ActionResult.SUCCESS;
            }
        }

        // Check the other groups. For every group, try consuming the ingredients,
        // see if it succeeds.
        for (Map.Entry<RecipeHandlerGroup, List<RecipeHandlerList>> handlerListEntry : handlerGroups.entrySet()) {
            if (handlerListEntry.getKey().equals(BUS_DISTINCT)) continue;

            // List to keep track of the remaining items for this RecipeHandlerGroup
            Map<RecipeCapability<?>, List<Object>> copiedRecipeContents = searchRecipeContents;
            boolean found = false;

            for (RecipeHandlerList handler : handlerListEntry.getValue()) {
                copiedRecipeContents = handler.handleRecipe(io, recipe, copiedRecipeContents, true);
                if (copiedRecipeContents.isEmpty()) {
                    found = true;
                    break;
                }
            }
            // If we're already in the bypass_distinct group, don't check it twice.
            if (!handlerListEntry.getKey().equals(BYPASS_DISTINCT)) {
                for (RecipeHandlerList bypassHandler : handlerGroups.getOrDefault(BYPASS_DISTINCT,
                        Collections.emptyList())) {
                    copiedRecipeContents = bypassHandler.handleRecipe(io, recipe, copiedRecipeContents, true);
                    if (copiedRecipeContents.isEmpty()) {
                        found = true;
                        break;
                    }
                }
            }

            if (!found) continue;
            if (simulated) return ActionResult.SUCCESS;
            // Start actually removing items.
            // Keep track of the remaining items for this RecipeHandlerGroup
            copiedRecipeContents = recipeContents;
            // First go through the handlers of the group
            for (RecipeHandlerList handler : handlerListEntry.getValue()) {
                copiedRecipeContents = handler.handleRecipe(io, recipe, copiedRecipeContents, false);
                if (copiedRecipeContents.isEmpty()) {
                    recipeContents.clear();
                    return ActionResult.SUCCESS;
                }
            }
            // Then go through the handlers that bypass the distinctness system and empty those
            // If we're already in the bypass_distinct group, don't check it twice.
            if (!handlerListEntry.getKey().equals(BYPASS_DISTINCT)) {
                for (RecipeHandlerList bypassHandler : handlerGroups.getOrDefault(BYPASS_DISTINCT,
                        Collections.emptyList())) {
                    copiedRecipeContents = bypassHandler.handleRecipe(io, recipe, copiedRecipeContents, false);
                    if (copiedRecipeContents.isEmpty()) {
                        recipeContents.clear();
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        for (var entry : recipeContents.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                return ActionResult.fail(null, entry.getKey(), io);
            }
        }

        return ActionResult.FAIL_NO_REASON;
    }
}
