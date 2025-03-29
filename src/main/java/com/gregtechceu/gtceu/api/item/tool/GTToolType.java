package com.gregtechceu.gtceu.api.item.tool;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.item.IGTTool;
import com.gregtechceu.gtceu.api.sound.ExistingSoundEntry;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.item.tool.behavior.*;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * @author Screret
 * @date 2023/2/23
 * @implNote GTToolType
 */
public class GTToolType {

    @Getter
    private static final Map<String, GTToolType> types = new HashMap<>();

    public static final GTToolType SWORD = GTToolType.builder("sword")
            .toolTag(ItemTags.SWORDS)
            .harvestTag(BlockTags.SWORD_EFFICIENT)
            .toolStats(b -> b.attacking().attackDamage(3.0F).attackSpeed(-2.4F))
            .constructor(GTSwordItem::create)
            .toolClassNames("sword")
            .materialAmount(2 * GTValues.M)
            .build();
    public static final GTToolType PICKAXE = GTToolType.builder("pickaxe")
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .toolStats(b -> b.blockBreaking().attackDamage(1.0F).attackSpeed(-2.8F)
                    .behaviors(TorchPlaceBehavior.INSTANCE))
            .toolClassNames("pickaxe")
            .materialAmount(3 * GTValues.M)
            .build();
    public static final GTToolType SHOVEL = GTToolType.builder("shovel")
            .toolTag(ItemTags.SHOVELS)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .toolStats(
                    b -> b.blockBreaking().attackDamage(1.5F).attackSpeed(-3.0F).behaviors(GrassPathBehavior.INSTANCE))
            .constructor(GTShovelItem::create)
            .toolClassNames("shovel")
            .materialAmount(GTValues.M)
            .build();
    public static final GTToolType AXE = GTToolType.builder("axe")
            .toolTag(ItemTags.AXES)
            .harvestTag(BlockTags.MINEABLE_WITH_AXE)
            .toolStats(b -> b.blockBreaking()
                    .attackDamage(5.0F).attackSpeed(-3.2F).baseEfficiency(2.0F)
                    .behaviors(DisableShieldBehavior.INSTANCE, TreeFellingBehavior.INSTANCE,
                            LogStripBehavior.INSTANCE,
                            ScrapeBehavior.INSTANCE, WaxOffBehavior.INSTANCE))
            .constructor(GTAxeItem::create)
            .toolClassNames("axe")
            .materialAmount(3 * GTValues.M)
            .build();
    public static final GTToolType HOE = GTToolType.builder("hoe")
            .toolTag(ItemTags.HOES)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.cannotAttack().attackSpeed(-1.0F).behaviors(HoeGroundBehavior.INSTANCE))
            .constructor(GTHoeItem::create)
            .toolClassNames("hoe")
            .materialAmount(2 * GTValues.M)
            .build();

    public static final GTToolType MINING_HAMMER = GTToolType.builder("mining_hammer")
            .toolTag(CustomTags.MINING_HAMMERS)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .toolStats(b -> b.blockBreaking().aoe(1, 1, 0)
                    .efficiencyMultiplier(0.4F).attackDamage(1.5F).attackSpeed(-3.2F)
                    .durabilityMultiplier(3.0F)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .toolClasses(GTToolType.PICKAXE)
            .materialAmount(6 * GTValues.M)
            .build();
    public static final GTToolType SPADE = GTToolType.builder("spade")
            .toolTag(CustomTags.SPADES)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .toolStats(b -> b.blockBreaking().aoe(1, 1, 0)
                    .efficiencyMultiplier(0.4F).attackDamage(1.5F).attackSpeed(-3.2F)
                    .durabilityMultiplier(3.0F)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, GrassPathBehavior.INSTANCE))
            .toolClasses(GTToolType.SHOVEL)
            .materialAmount(3 * GTValues.M)
            .build();
    public static final GTToolType SCYTHE = GTToolType.builder("scythe")
            .toolTag(CustomTags.SCYTHES)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().attacking()
                    .attackDamage(5.0F).attackSpeed(-3.0F).durabilityMultiplier(3.0F)
                    .aoe(2, 2, 2)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, HoeGroundBehavior.INSTANCE, HarvestCropsBehavior.INSTANCE)
                    .canApplyEnchantment(EnchantmentCategory.DIGGER))
            .constructor(GTHoeItem::create)
            .toolClassNames("scythe")
            .toolClasses(GTToolType.HOE)
            .materialAmount(3 * GTValues.M)
            .build();

    public static final GTToolType SAW = GTToolType.builder("saw")
            .toolTag(CustomTags.CRAFTING_SAWS)
            .toolTag(CustomTags.SAWS)
            .harvestTag(CustomTags.MINEABLE_WITH_SAW)
            .toolStats(b -> b.crafting().damagePerCraftingAction(2)
                    .attackDamage(-1.0F).attackSpeed(-2.6F)
                    .behaviors(HarvestIceBehavior.INSTANCE))
            .sound(GTSoundEntries.SAW_TOOL)
            .symbol('s')
            .materialAmount(2 * GTValues.M)
            .build();
    public static final GTToolType HARD_HAMMER = GTToolType.builder("hammer")
            .toolTag(CustomTags.CRAFTING_HAMMERS)
            .toolTag(CustomTags.HAMMERS)
            .harvestTag(CustomTags.MINEABLE_WITH_HAMMER)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .toolStats(b -> b.blockBreaking().crafting().damagePerCraftingAction(2)
                    .attackDamage(1.0F).attackSpeed(-2.8F)
                    .behaviors(new EntityDamageBehavior(2.0F, IronGolem.class)))
            .sound(GTSoundEntries.FORGE_HAMMER)
            .symbol('h')
            .toolClasses(GTToolType.PICKAXE)
            .materialAmount(6 * GTValues.M)
            .build();
    public static final GTToolType SOFT_MALLET = GTToolType.builder("mallet")
            .toolTag(CustomTags.CRAFTING_MALLETS)
            .toolTag(CustomTags.MALLETS)
            .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F).sneakBypassUse()
                    .behaviors(ToolModeSwitchBehavior.INSTANCE))
            .sound(GTSoundEntries.SOFT_MALLET_TOOL)
            .symbol('r')
            .materialAmount(6 * GTValues.M)
            .build();
    public static final GTToolType WRENCH = GTToolType.builder("wrench")
            .toolTag(CustomTags.CRAFTING_WRENCHES)
            .toolTag(CustomTags.WRENCHES)
            .toolTag(CustomTags.WRENCH)
            .harvestTag(CustomTags.MINEABLE_WITH_WRENCH)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .attackDamage(1.0F).attackSpeed(-2.8F)
                    .behaviors(BlockRotatingBehavior.INSTANCE, new EntityDamageBehavior(3.0F, IronGolem.class),
                            ToolModeSwitchBehavior.INSTANCE))
            .sound(GTSoundEntries.WRENCH_TOOL, true)
            .symbol('w')
            .materialAmount(4 * GTValues.M)
            .build();
    public static final GTToolType FILE = GTToolType.builder("file")
            .toolTag(CustomTags.CRAFTING_FILES)
            .toolTag(CustomTags.FILES)
            .toolStats(b -> b.crafting().damagePerCraftingAction(4)
                    .cannotAttack().attackSpeed(-2.4F))
            .sound(GTSoundEntries.FILE_TOOL)
            .symbol('f')
            .materialAmount(2 * GTValues.M)
            .build();
    public static final GTToolType CROWBAR = GTToolType.builder("crowbar")
            .toolTag(CustomTags.CRAFTING_CROWBARS)
            .toolTag(CustomTags.CROWBARS)
            .harvestTag(CustomTags.MINEABLE_WITH_CROWBAR)
            .toolStats(b -> b.blockBreaking().crafting()
                    .attackDamage(2.0F).attackSpeed(-2.4F)
                    .sneakBypassUse().behaviors(RotateRailBehavior.INSTANCE))
            .sound(new ExistingSoundEntry(SoundEvents.ITEM_BREAK, SoundSource.BLOCKS), true)
            .symbol('c')
            .materialAmount(3 * GTValues.M / 2)
            .build();
    public static final GTToolType SCREWDRIVER = GTToolType.builder("screwdriver")
            .toolTag(CustomTags.CRAFTING_SCREWDRIVERS)
            .toolTag(CustomTags.SCREWDRIVERS)
            .toolStats(b -> b.crafting().damagePerCraftingAction(4).sneakBypassUse()
                    .attackDamage(-1.0F).attackSpeed(3.0F)
                    .behaviors(new EntityDamageBehavior(3.0F, Spider.class)))
            .sound(GTSoundEntries.SCREWDRIVER_TOOL)
            .symbol('d')
            .materialAmount(GTValues.M)
            .build();
    public static final GTToolType MORTAR = GTToolType.builder("mortar")
            .toolTag(CustomTags.CRAFTING_MORTARS)
            .toolTag(CustomTags.MORTARS)
            .toolStats(b -> b.crafting().damagePerCraftingAction(2).cannotAttack().attackSpeed(-2.4F))
            .sound(GTSoundEntries.MORTAR_TOOL)
            .symbol('m')
            .materialAmount(2 * GTValues.M)
            .build();
    public static final GTToolType WIRE_CUTTER = GTToolType.builder("wire_cutter")
            .toolTag(CustomTags.CRAFTING_WIRE_CUTTERS)
            .toolTag(CustomTags.WIRE_CUTTERS)
            .harvestTag(CustomTags.MINEABLE_WITH_WIRE_CUTTER)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .damagePerCraftingAction(4).attackDamage(-1.0F).attackSpeed(-2.4F))
            .sound(GTSoundEntries.WIRECUTTER_TOOL, true)
            .symbol('x')
            .materialAmount(4 * GTValues.M) // 3 plates + 2 rods
            .build();
    public static final GTToolType KNIFE = GTToolType.builder("knife")
            .toolTag(CustomTags.CRAFTING_KNIVES)
            .toolTag(CustomTags.KNIVES)
            .harvestTag(CustomTags.MINEABLE_WITH_KNIFE)
            .toolStats(b -> b.crafting().attacking().attackSpeed(3.0F))
            .constructor(GTSwordItem::create)
            .symbol('k')
            .toolClasses(GTToolType.SWORD)
            .materialAmount(GTValues.M)
            .build();
    public static final GTToolType BUTCHERY_KNIFE = GTToolType.builder("butchery_knife")
            .toolTag(CustomTags.BUTCHERY_KNIVES)
            .toolStats(b -> b.attacking().attackDamage(1.5F).attackSpeed(-1.3F)
                    .defaultEnchantment(Enchantments.MOB_LOOTING, 3))
            .constructor(GTSwordItem::create)
            .materialAmount(4 * GTValues.M)
            .build();
    // public static GTToolType GRAFTER = new GTToolType("grafter", 1, 1, GTCEu.id("item/tools/handle_hammer"),
    // GTCEu.id("item/tools/hammer"));
    public static final GTToolType PLUNGER = GTToolType.builder("plunger")
            .toolTag(CustomTags.PLUNGERS)
            .toolStats(b -> b.cannotAttack().attackSpeed(-2.4F).sneakBypassUse()
                    .behaviors(PlungerBehavior.INSTANCE))
            .sound(GTSoundEntries.PLUNGER_TOOL)
            .build();
    public static final GTToolType SHEARS = GTToolType.builder("shears")
            .toolTag(CustomTags.SHEARS)
            .harvestTag(CustomTags.MINEABLE_WITH_SHEARS)
            .toolStats(b -> b)
            .build();
    public static final GTToolType DRILL_LV = GTToolType.builder("lv_drill")
            .idFormat("lv_%s_drill")
            .toolTag(CustomTags.DRILLS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().aoe(1, 1, 0)
                    .attackDamage(1.0F).attackSpeed(-3.2F).durabilityMultiplier(3.0F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .sound(GTSoundEntries.DRILL_TOOL, true)
            .electric(GTValues.LV)
            .toolClassNames("drill")
            .build();
    public static final GTToolType DRILL_MV = GTToolType.builder("mv_drill")
            .idFormat("mv_%s_drill")
            .toolTag(CustomTags.DRILLS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().aoe(1, 1, 2)
                    .attackDamage(1.0F).attackSpeed(-3.2F).durabilityMultiplier(4.0F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_MV)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .sound(GTSoundEntries.DRILL_TOOL, true)
            .electric(GTValues.MV)
            .toolClassNames("drill")
            .build();
    public static final GTToolType DRILL_HV = GTToolType.builder("hv_drill")
            .idFormat("hv_%s_drill")
            .toolTag(CustomTags.DRILLS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().aoe(2, 2, 4)
                    .attackDamage(1.0F).attackSpeed(-3.2F).durabilityMultiplier(5.0F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_HV)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .sound(GTSoundEntries.DRILL_TOOL, true)
            .electric(GTValues.HV)
            .toolClassNames("drill")
            .build();
    public static final GTToolType DRILL_EV = GTToolType.builder("ev_drill")
            .idFormat("ev_%s_drill")
            .toolTag(CustomTags.DRILLS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().aoe(3, 3, 6)
                    .attackDamage(1.0F).attackSpeed(-3.2F).durabilityMultiplier(6.0F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_EV)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .sound(GTSoundEntries.DRILL_TOOL, true)
            .electric(GTValues.EV)
            .toolClassNames("drill")
            .build();
    public static final GTToolType DRILL_IV = GTToolType.builder("iv_drill")
            .idFormat("iv_%s_drill")
            .toolTag(CustomTags.DRILLS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().aoe(4, 4, 8)
                    .attackDamage(1.0F).attackSpeed(-3.2F).durabilityMultiplier(7.0F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_IV)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .sound(GTSoundEntries.DRILL_TOOL, true)
            .electric(GTValues.IV)
            .toolClassNames("drill")
            .build();
    public static final GTToolType CHAINSAW_LV = GTToolType.builder("lv_chainsaw")
            .idFormat("lv_%s_chainsaw")
            .toolTag(ItemTags.AXES)
            .toolTag(CustomTags.CHAINSAWS)
            .harvestTag(BlockTags.MINEABLE_WITH_AXE)
            .harvestTag(BlockTags.SWORD_EFFICIENT)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking()
                    .efficiencyMultiplier(2.0F)
                    .attackDamage(5.0F).attackSpeed(-3.2F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV)
                    .behaviors(HarvestIceBehavior.INSTANCE, DisableShieldBehavior.INSTANCE,
                            TreeFellingBehavior.INSTANCE))
            .sound(GTSoundEntries.CHAINSAW_TOOL, true)
            .electric(GTValues.LV)
            .toolClasses(GTToolType.AXE)
            .build();
    public static final GTToolType WRENCH_LV = GTToolType.builder("lv_wrench")
            .idFormat("lv_%s_wrench")
            .toolTag(CustomTags.CRAFTING_WRENCHES)
            .toolTag(CustomTags.WRENCHES)
            .toolTag(CustomTags.WRENCH)
            .harvestTag(CustomTags.MINEABLE_WITH_WRENCH)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .efficiencyMultiplier(2.0F)
                    .attackDamage(1.0F).attackSpeed(-2.8F)
                    .behaviors(BlockRotatingBehavior.INSTANCE, new EntityDamageBehavior(3.0F, IronGolem.class),
                            ToolModeSwitchBehavior.INSTANCE)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV))
            .sound(GTSoundEntries.WRENCH_TOOL, true)
            .electric(GTValues.LV)
            .toolClasses(GTToolType.WRENCH)
            .build();
    public static final GTToolType WRENCH_HV = GTToolType.builder("hv_wrench")
            .idFormat("hv_%s_wrench")
            .toolTag(CustomTags.CRAFTING_WRENCHES)
            .toolTag(CustomTags.WRENCHES)
            .toolTag(CustomTags.WRENCH)
            .harvestTag(CustomTags.MINEABLE_WITH_WRENCH)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .efficiencyMultiplier(3.0F)
                    .attackDamage(1.0F).attackSpeed(-2.8F)
                    .behaviors(BlockRotatingBehavior.INSTANCE, new EntityDamageBehavior(3.0F, IronGolem.class),
                            ToolModeSwitchBehavior.INSTANCE)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_HV))
            .sound(GTSoundEntries.WRENCH_TOOL, true)
            .electric(GTValues.HV)
            .toolClasses(GTToolType.WRENCH)
            .build();
    public static final GTToolType WRENCH_IV = GTToolType.builder("iv_wrench")
            .idFormat("iv_%s_wrench")
            .toolTag(CustomTags.CRAFTING_WRENCHES)
            .toolTag(CustomTags.WRENCHES)
            .toolTag(CustomTags.WRENCH)
            .harvestTag(CustomTags.MINEABLE_WITH_WRENCH)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .efficiencyMultiplier(4.0F)
                    .attackDamage(1.0F).attackSpeed(-2.8F)
                    .behaviors(BlockRotatingBehavior.INSTANCE, new EntityDamageBehavior(3.0F, IronGolem.class),
                            ToolModeSwitchBehavior.INSTANCE)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_IV))
            .sound(GTSoundEntries.WRENCH_TOOL, true)
            .electric(GTValues.IV)
            .toolClasses(GTToolType.WRENCH)
            .build();

    public static final GTToolType WIRE_CUTTER_LV = GTToolType.builder("lv_wirecutter")
            .idFormat("lv_%s_wire_cutter")
            .toolTag(CustomTags.CRAFTING_WIRE_CUTTERS)
            .toolTag(CustomTags.WIRE_CUTTERS)
            .harvestTag(CustomTags.MINEABLE_WITH_WIRE_CUTTER)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .damagePerCraftingAction(4).attackDamage(-1.0F).attackSpeed(-2.4F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV))
            .sound(GTSoundEntries.WIRECUTTER_TOOL, true)
            .electric(GTValues.LV)
            .toolClasses(GTToolType.WIRE_CUTTER)
            .build();

    public static final GTToolType WIRE_CUTTER_HV = GTToolType.builder("hv_wirecutter")
            .idFormat("hv_%s_wire_cutter")
            .toolTag(CustomTags.CRAFTING_WIRE_CUTTERS)
            .toolTag(CustomTags.WIRE_CUTTERS)
            .harvestTag(CustomTags.MINEABLE_WITH_WIRE_CUTTER)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .damagePerCraftingAction(4).attackDamage(-1.0F).attackSpeed(-2.4F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_HV))
            .sound(GTSoundEntries.WIRECUTTER_TOOL, true)
            .electric(GTValues.HV)
            .toolClasses(GTToolType.WIRE_CUTTER)
            .build();

    public static final GTToolType WIRE_CUTTER_IV = GTToolType.builder("iv_wirecutter")
            .idFormat("iv_%s_wire_cutter")
            .toolTag(CustomTags.CRAFTING_WIRE_CUTTERS)
            .toolTag(CustomTags.WIRE_CUTTERS)
            .harvestTag(CustomTags.MINEABLE_WITH_WIRE_CUTTER)
            .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                    .damagePerCraftingAction(4).attackDamage(-1.0F).attackSpeed(-2.4F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_IV))
            .sound(GTSoundEntries.WIRECUTTER_TOOL, true)
            .electric(GTValues.IV)
            .toolClasses(GTToolType.WIRE_CUTTER)
            .build();
    public static final GTToolType BUZZSAW = GTToolType.builder("buzzsaw")
            .toolTag(CustomTags.CRAFTING_SAWS)
            .toolTag(CustomTags.SAWS)
            .toolTag(CustomTags.BUZZSAWS)
            .toolStats(b -> b.crafting().attackDamage(1.5F).attackSpeed(-3.2F)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV))
            .sound(GTSoundEntries.CHAINSAW_TOOL, true)
            .electric(GTValues.LV)
            .toolClasses(GTToolType.SAW)
            .build();
    public static final GTToolType SCREWDRIVER_LV = GTToolType.builder("lv_screwdriver")
            .idFormat("lv_%s_screwdriver")
            .toolTag(CustomTags.CRAFTING_SCREWDRIVERS)
            .toolTag(CustomTags.SCREWDRIVERS)
            .toolStats(b -> b.crafting().sneakBypassUse()
                    .attackDamage(-1.0F).attackSpeed(3.0F)
                    .behaviors(new EntityDamageBehavior(3.0F, Spider.class))
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV))
            .sound(GTSoundEntries.SCREWDRIVER_TOOL)
            .electric(GTValues.LV)
            .toolClasses(GTToolType.SCREWDRIVER)
            .build();

    public final String name;
    public final String idFormat;
    // at least one has to be set. first one MUST be the main tag.
    public final List<TagKey<Item>> itemTags;
    public final List<TagKey<Block>> harvestTags;
    public final ResourceLocation modelLocation;
    public final Set<String> toolClassNames;
    public final Set<GTToolType> toolClasses;
    @Nullable
    public final SoundEntry soundEntry;
    public final boolean playSoundOnBlockDestroy;
    public final Character symbol;
    public final long materialAmount;
    public final IGTToolDefinition toolDefinition;
    public final ToolConstructor constructor;
    public final int electricTier;

    public GTToolType(String name, String idFormat, Character symbol, Set<GTToolType> toolClasses,
                      IGTToolDefinition toolDefinition, ToolConstructor constructor, List<TagKey<Block>> harvestTags,
                      List<TagKey<Item>> itemTags, ResourceLocation modelLocation, Set<String> toolClassNames,
                      @Nullable SoundEntry soundEntry, boolean playSoundOnBlockDestroy, int electricTier,
                      long materialAmount) {
        this.name = name;
        this.idFormat = idFormat;
        this.symbol = symbol;
        toolClasses.add(this);
        this.toolClasses = toolClasses;
        this.toolDefinition = toolDefinition;
        this.constructor = constructor;
        this.itemTags = itemTags;
        this.harvestTags = harvestTags;
        this.modelLocation = modelLocation;
        this.toolClassNames = toolClassNames;
        this.soundEntry = soundEntry;
        this.playSoundOnBlockDestroy = playSoundOnBlockDestroy;
        this.electricTier = electricTier;
        this.materialAmount = materialAmount;

        types.put(name, this);
    }

    public boolean is(ItemStack itemStack) {
        return ToolHelper.is(itemStack, this);
    }

    public String getUnlocalizedName() {
        return "item.gtceu.tool." + name;
    }

    @FunctionalInterface
    public interface ToolConstructor {

        IGTTool apply(GTToolType type, MaterialToolTier tier, Material material, IGTToolDefinition definition,
                      Item.Properties properties);
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    @Accessors(fluent = true, chain = true)
    public static class Builder {

        private final String name;
        @Setter
        private String idFormat;

        private final List<TagKey<Item>> itemTags = new ArrayList<>();
        private final List<TagKey<Block>> harvestTags = new ArrayList<>();

        @Setter
        private Set<String> toolClassNames = new HashSet<>();
        private Set<GTToolType> toolClasses = new HashSet<>();
        @Setter
        private IGTToolDefinition toolStats;
        @Setter
        private long materialAmount;
        @Setter
        private int tier = -1;
        @Setter
        private Character symbol = null;
        @Setter
        private ToolConstructor constructor = GTToolItem::create;
        @Setter
        private ResourceLocation modelLocation;
        private SoundEntry sound;
        private boolean playSoundOnBlockDestroy;

        public Builder(String name) {
            this.name = name;
            this.idFormat = "%s_" + name;
            this.modelLocation = GTCEu.id("item/tools/" + name);
        }

        @SafeVarargs
        public final Builder toolTag(TagKey<Item>... tags) {
            itemTags.addAll(Arrays.stream(tags).toList());
            return this;
        }

        @SafeVarargs
        public final Builder harvestTag(TagKey<Block>... tags) {
            harvestTags.addAll(Arrays.stream(tags).toList());
            return this;
        }

        @Tolerate
        public Builder toolClasses(GTToolType... classes) {
            this.toolClasses.addAll(Arrays.stream(classes).toList());
            this.toolClassNames.addAll(Arrays.stream(classes).map(type -> type.name).toList());
            return this;
        }

        @Tolerate
        public Builder toolClassNames(String... classes) {
            this.toolClassNames.addAll(Arrays.stream(classes).toList());
            return this;
        }

        @Tolerate
        public Builder toolStats(UnaryOperator<ToolDefinitionBuilder> builder) {
            this.toolStats = builder.apply(new ToolDefinitionBuilder()).build();
            return this;
        }

        public Builder sound(SoundEntry sound) {
            return this.sound(sound, false);
        }

        public Builder sound(SoundEntry sound, boolean playSoundOnBlockDestroy) {
            this.sound = sound;
            this.playSoundOnBlockDestroy = playSoundOnBlockDestroy;
            return this;
        }

        public Builder electric(int tier) {
            return tier(tier);
        }

        private GTToolType get() {
            return new GTToolType(name,
                    idFormat,
                    symbol,
                    toolClasses,
                    toolStats,
                    constructor,
                    harvestTags,
                    itemTags,
                    modelLocation,
                    toolClassNames,
                    sound,
                    playSoundOnBlockDestroy,
                    tier,
                    materialAmount);
        }

        public GTToolType build() {
            if (toolClassNames.isEmpty()) {
                toolClassNames.add(name);
            }
            if (this.symbol == null) {
                return get();
            }
            GTToolType existing = ToolHelper.getToolFromSymbol(this.symbol);
            if (existing != null) {
                throw new IllegalArgumentException(
                        String.format("Symbol %s has been taken by %s already!", symbol, existing));
            }
            GTToolType supplied = get();
            ToolHelper.registerToolSymbol(this.symbol, supplied);
            return supplied;
        }
    }
}
