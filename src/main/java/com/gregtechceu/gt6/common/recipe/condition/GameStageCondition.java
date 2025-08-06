package com.gregtechceu.gt6.common.recipe.condition;

import com.gregtechceu.gt6.api.machine.trait.RecipeLogic;
import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.RecipeCondition;
import com.gregtechceu.gt6.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gt6.common.data.GTRecipeConditions;
import com.gregtechceu.gt6.common.machine.owner.MachineOwner;

import net.darkhax.gamestages.data.GameStageSaveHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class GameStageCondition extends RecipeCondition {

    public static final Codec<GameStageCondition> CODEC = RecordCodecBuilder
            .create(instance -> RecipeCondition.isReverse(instance)
                    .and(Codec.STRING.fieldOf("stageName").forGetter(val -> val.stageName))
                    .apply(instance, GameStageCondition::new));

    private String stageName;

    public final static GameStageCondition INSTANCE = new GameStageCondition();

    public GameStageCondition(String stageName) {
        this(false, stageName);
    }

    public GameStageCondition(boolean isReverse, String stageName) {
        super(isReverse);
        this.stageName = stageName;
    }

    @Override
    public RecipeConditionType<?> getType() {
        return GTRecipeConditions.GAMESTAGE;
    }

    @Override
    public Component getTooltips() {
        if (isReverse) return Component.translatable("recipe.condition.gamestage.locked_stage", stageName);
        return Component.translatable("recipe.condition.gamestage.unlocked_stage", stageName);
    }

    @Override
    public boolean testCondition(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        MachineOwner owner = recipeLogic.machine.self().getOwner();
        if (owner == null) return false;
        for (var player : owner.getMembers()) {
            var playerData = GameStageSaveHandler.getPlayerData(player);
            if (playerData != null && playerData.hasStage(stageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RecipeCondition createTemplate() {
        return new GameStageCondition();
    }

    @Override
    public @NotNull JsonObject serialize() {
        var obj = super.serialize();
        obj.addProperty("stageName", stageName);
        return obj;
    }

    @Override
    public RecipeCondition deserialize(@NotNull JsonObject config) {
        super.deserialize(config);
        stageName = GsonHelper.getAsString(config, "stageName");
        return this;
    }

    @Override
    public RecipeCondition fromNetwork(FriendlyByteBuf buf) {
        super.fromNetwork(buf);
        stageName = buf.readUtf();
        return this;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        super.toNetwork(buf);
        buf.writeUtf(stageName);
    }
}
