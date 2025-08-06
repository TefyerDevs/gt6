package com.gregtechceu.gt6.common.recipe.condition;

import com.gregtechceu.gt6.api.machine.trait.RecipeLogic;
import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.RecipeCondition;
import com.gregtechceu.gt6.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gt6.common.data.GTRecipeConditions;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class BiomeCondition extends RecipeCondition {

    public static final Codec<BiomeCondition> CODEC = RecordCodecBuilder
            .create(instance -> RecipeCondition.isReverse(instance)
                    .and(ResourceKey.codec(Registries.BIOME).fieldOf("biome").forGetter(val -> val.biome))
                    .apply(instance, BiomeCondition::new));

    public final static BiomeCondition INSTANCE = new BiomeCondition();
    @Getter
    private ResourceKey<Biome> biome = ResourceKey.create(Registries.BIOME, new ResourceLocation("dummy"));

    public BiomeCondition(boolean isReverse, ResourceKey<Biome> biome) {
        super(isReverse);
        this.biome = biome;
    }

    public BiomeCondition(ResourceKey<Biome> biome) {
        this.biome = biome;
    }

    @Override
    public RecipeConditionType<?> getType() {
        return GTRecipeConditions.BIOME;
    }

    @Override
    public boolean isOr() {
        return true;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("recipe.condition.biome.tooltip",
                Component.translatableWithFallback(biome.location().toLanguageKey("biome"),
                        biome.location().toString()));
    }

    @Override
    public boolean testCondition(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.machine.self().getLevel();
        if (level == null) return false;
        Holder<Biome> biome = level.getBiome(recipeLogic.machine.self().getPos());
        return biome.is(this.biome);
    }

    @Override
    public RecipeCondition createTemplate() {
        return new BiomeCondition();
    }

    @NotNull
    @Override
    public JsonObject serialize() {
        JsonObject config = super.serialize();
        config.addProperty("biome", biome.location().toString());
        return config;
    }

    @Override
    public RecipeCondition deserialize(@NotNull JsonObject config) {
        super.deserialize(config);
        biome = ResourceKey.create(Registries.BIOME,
                new ResourceLocation(GsonHelper.getAsString(config, "biome", "dummy")));
        return this;
    }

    @Override
    public RecipeCondition fromNetwork(FriendlyByteBuf buf) {
        super.fromNetwork(buf);
        biome = buf.readResourceKey(Registries.BIOME);
        return this;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        super.toNetwork(buf);
        buf.writeResourceKey(biome);
    }
}
