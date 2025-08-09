package com.gregtechceu.gt6.common.commands.arguments;

import com.gregtechceu.gt6.api.GTAPI;
import com.gregtechceu.gt6.api.data.chemical.material.Material;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class MaterialArgument implements ArgumentType<Material> {

    private static final Collection<String> EXAMPLES = Arrays.asList("aluminium", "gt6:steel");

    public MaterialArgument() {}

    public static MaterialArgument material() {
        return new MaterialArgument();
    }

    @Override
    public Material parse(StringReader reader) throws CommandSyntaxException {
        return MaterialParser.parseForMaterial(GTAPI.materialManager, reader);
    }

    public static <S> Material getMaterial(CommandContext<S> context, String name) {
        return context.getArgument(name, Material.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return MaterialParser.fillSuggestions(GTAPI.materialManager, builder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
