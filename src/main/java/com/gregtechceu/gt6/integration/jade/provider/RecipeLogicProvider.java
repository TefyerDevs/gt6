package com.gregtechceu.gt6.integration.jade.provider;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gt6.api.capability.GTCapabilityHelper;
import com.gregtechceu.gt6.api.machine.steam.SimpleSteamMachine;
import com.gregtechceu.gt6.api.machine.trait.RecipeLogic;
import com.gregtechceu.gt6.api.recipe.RecipeHelper;
import com.gregtechceu.gt6.client.util.TooltipHelper;
import com.gregtechceu.gt6.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.gregtechceu.gt6.utils.FormattingUtil;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class RecipeLogicProvider extends CapabilityBlockProvider<RecipeLogic> {

    public RecipeLogicProvider() {
        super(GTCEu.id("recipe_logic_provider"));
    }

    @Nullable
    @Override
    protected RecipeLogic getCapability(Level level, BlockPos pos, @Nullable Direction side) {
        return GTCapabilityHelper.getRecipeLogic(level, pos, side);
    }

    @Override
    protected void write(CompoundTag data, RecipeLogic capability) {
        data.putBoolean("Working", capability.isWorking());
        var recipeInfo = new CompoundTag();
        var recipe = capability.getLastRecipe();
        if (recipe != null) {
            var EUt = RecipeHelper.getRealEUtWithIO(recipe);

            recipeInfo.putLong("EUt", EUt.getTotalEU());
            recipeInfo.putLong("voltage", EUt.voltage());
            recipeInfo.putBoolean("isInput", EUt.isInput());
        }

        if (!recipeInfo.isEmpty()) {
            data.put("Recipe", recipeInfo);
        }
    }

    @Override
    protected void addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block,
                              BlockEntity blockEntity, IPluginConfig config) {
        if (capData.getBoolean("Working")) {
            var recipeInfo = capData.getCompound("Recipe");
            if (!recipeInfo.isEmpty()) {
                var EUt = recipeInfo.getLong("EUt");
                var isInput = recipeInfo.getBoolean("isInput");
                boolean isSteam = false;

                if (blockEntity instanceof MetaMachineBlockEntity mbe) {
                    var machine = mbe.getMetaMachine();
                    if (machine instanceof SimpleSteamMachine ssm) {
                        EUt = (long) (EUt * ssm.getConversionRate());
                        isSteam = true;
                    } else if (machine instanceof SteamParallelMultiblockMachine smb) {
                        EUt = (long) (EUt * smb.getConversionRate());
                        isSteam = true;
                    }
                }

                if (EUt > 0) {
                    MutableComponent text;

                    if (isSteam) {
                        text = Component.translatable("gt6.jade.fluid_use", FormattingUtil.formatNumbers(EUt))
                                .withStyle(ChatFormatting.GREEN);
                    } else {
                        var voltage = recipeInfo.getLong("voltage");
                        var tier = GTUtil.getTierByVoltage(voltage);
                        float minAmperage = (float) EUt / GTValues.V[tier];

                        text = Component
                                .translatable("gt6.jade.amperage_use",
                                        FormattingUtil.formatNumber2Places(minAmperage))
                                .withStyle(ChatFormatting.RED)
                                .append(Component.translatable("gt6.jade.at").withStyle(ChatFormatting.GREEN));
                        if (tier < GTValues.TIER_COUNT) {
                            text = text.append(Component.literal(GTValues.VNF[tier])
                                    .withStyle(style -> style.withColor(GTValues.VC[tier])));
                        } else {
                            int speed = Mth.clamp(tier - GTValues.TIER_COUNT - 1, 0, GTValues.TIER_COUNT);
                            text = text.append(Component.literal("MAX")
                                    .withStyle(style -> style.withColor(TooltipHelper.rainbowColor(speed)))
                                    .append(Component.literal("+")
                                            .withStyle(style -> style.withColor(GTValues.VC[speed]))
                                            .append(FormattingUtil.formatNumbers(speed))));

                        }
                        text.append(Component.translatable("gt6.universal.padded_parentheses",
                                (Component.translatable("gt6.recipe.eu.total",
                                        FormattingUtil.formatNumbers(EUt))))
                                .withStyle(ChatFormatting.WHITE));
                    }

                    if (isInput) {
                        tooltip.add(Component.translatable("gt6.top.energy_consumption").append(" ").append(text));
                    } else {
                        tooltip.add(Component.translatable("gt6.top.energy_production").append(" ").append(text));
                    }
                }
            }
        }
    }
}
