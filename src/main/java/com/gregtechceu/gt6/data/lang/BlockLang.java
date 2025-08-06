package com.gregtechceu.gt6.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

import static com.gregtechceu.gt6.data.lang.LangHandler.replace;

public class BlockLang {

    public static void init(RegistrateLangProvider provider) {
        initCasingLang(provider);
    }

    private static void initCasingLang(RegistrateLangProvider provider) {
        provider.add("block.gt6.lamp.tooltip.inverted", "Inverted");
        provider.add("block.gt6.lamp.tooltip.no_bloom", "No Bloom");
        provider.add("block.gt6.lamp.tooltip.no_light", "No Light");

        // Coils
        replace(provider, "block.gt6.hssg_coil_block", "HSS-G Coil Block");
        replace(provider, "block.gt6.rtm_alloy_coil_block", "RTM Alloy Coil Block");

        replace(provider, "block.gt6.wire_coil.tooltip_extended_info", "§7Hold SHIFT to show Coil Bonus Info");
        replace(provider, "block.gt6.wire_coil.tooltip_heat", "§cBase Heat Capacity: §f%d K");
        replace(provider, "block.gt6.wire_coil.tooltip_smelter", "§8Multi Smelter:");
        replace(provider, "block.gt6.wire_coil.tooltip_parallel_smelter", "  §5Max Parallel: §f%s");
        replace(provider, "block.gt6.wire_coil.tooltip_energy_smelter", "  §aEnergy Usage: §f%s EU/t §8per recipe");
        replace(provider, "block.gt6.wire_coil.tooltip_pyro", "§8Pyrolyse Oven:");
        replace(provider, "block.gt6.wire_coil.tooltip_speed_pyro", "  §bProcessing Speed: §f%s%%");
        replace(provider, "block.gt6.wire_coil.tooltip_cracking", "§8Cracking Unit:");
        replace(provider, "block.gt6.wire_coil.tooltip_energy_cracking", "  §aEnergy Usage: §f%s%%");

        // Substation capacitors
        provider.add("block.gt6.substation_capacitor.tooltip_empty", "§7For filling space in your Power Substation");
        provider.add("block.gt6.substation_capacitor.tooltip_filled", "§cEnergy Capacity: §f%d EU");

        // Casings
        replace(provider, "block.gt6.bronze_brick_casing", "Bricked Bronze Casing");
        replace(provider, "block.gt6.steel_brick_casing", "Bricked Wrought Iron Casing");
        replace(provider, "block.gt6.heatproof_machine_casing", "Heat Proof Invar Machine Casing");
        replace(provider, "block.gt6.frostproof_machine_casing", "Frost Proof Aluminium Machine Casing");
        replace(provider, "block.gt6.steel_machine_casing", "Solid Steel Machine Casing");
        replace(provider, "block.gt6.clean_machine_casing", "Clean Stainless Steel Casing");
        replace(provider, "block.gt6.stable_machine_casing", "Stable Titanium Machine Casing");
        replace(provider, "block.gt6.robust_machine_casing", "Robust Tungstensteel Machine Casing");
        replace(provider, "block.gt6.casing_coke_bricks", "Coke Oven Bricks");
        replace(provider, "block.gt6.inert_machine_casing", "Chemically Inert PTFE Machine Casing");
        replace(provider, "block.gt6.sturdy_machine_casing", "Sturdy HSS-E Machine Casing");
        replace(provider, "block.gt6.casing_grate", "Grate Machine Casing");
        replace(provider, "block.gt6.assembly_line_unit", "Assembly Control Casing");
        replace(provider, "block.gt6.ptfe_pipe_casing", "PTFE Pipe Casing");
        replace(provider, "block.gt6.bronze_gearbox", "Bronze Gearbox Casing");
        replace(provider, "block.gt6.steel_gearbox", "Steel Gearbox Casing");
        replace(provider, "block.gt6.stainless_steel_gearbox", "Stainless Steel Gearbox Casing");
        replace(provider, "block.gt6.titanium_gearbox", "Titanium Gearbox Casing");
        replace(provider, "block.gt6.tungstensteel_gearbox", "Tungstensteel Gearbox Casing");
        replace(provider, "block.gt6.steel_turbine_casing", "Magnalium Turbine Casing");
        replace(provider, "block.gt6.titanium_turbine_casing", "Titanium Turbine Casing");
        replace(provider, "block.gt6.stainless_steel_turbine_casing", "Stainless Turbine Casing");
        replace(provider, "block.gt6.tungstensteel_turbine_casing", "Tungstensteel Turbine Casing");
        replace(provider, "block.gt6.bronze_pipe_casing", "Bronze Pipe Casing");
        replace(provider, "block.gt6.steel_pipe_casing", "Steel Pipe Casing");
        replace(provider, "block.gt6.titanium_pipe_casing", "Titanium Pipe Casing");
        replace(provider, "block.gt6.tungstensteel_pipe_casing", "Tungstensteel Pipe Casing");
        replace(provider, "block.gt6.palladium_substation", "Palladium Substation Casing");

        replace(provider, "block.gt6.steam_casing_bronze", "Bronze Hull");
        provider.add("block.gt6.steam_casing_bronze.tooltip", "§7For your first Steam Machines");
        replace(provider, "block.gt6.steam_casing_bricked_bronze", "Bricked Bronze Hull");
        provider.add("block.gt6.steam_casing_bricked_bronze.tooltip", "§7For your first Steam Machines");
        replace(provider, "block.gt6.steam_casing_steel", "Steel Hull");
        provider.add("block.gt6.steam_casing_steel.tooltip", "§7For improved Steam Machines");
        replace(provider, "block.gt6.steam_casing_bricked_steel", "Bricked Wrought Iron Hull");
        provider.add("block.gt6.steam_casing_bricked_steel.tooltip", "§7For improved Steam Machines");

        // GCYM Casings
        replace(provider, "block.gt6.laser_safe_engraving_casing", "Laser-Safe Engraving Casing");
        replace(provider, "block.gt6.large_scale_assembler_casing", "Large-Scale Assembler Casing");
        replace(provider, "block.gt6.reaction_safe_mixing_casing", "Reaction-Safe Mixing Casing");
        replace(provider, "block.gt6.vibration_safe_casing", "Vibration-Safe Casing");

        replace(provider, "block.gt6.superconducting_coil", "Superconducting Coil Block");
        replace(provider, "block.gt6.fusion_coil", "Fusion Coil Block");
        replace(provider, "block.gt6.fusion_casing", "Fusion Machine Casing");
        replace(provider, "block.gt6.fusion_casing_mk2", "Fusion Machine Casing MK II");
        replace(provider, "block.gt6.fusion_casing_mk3", "Fusion Machine Casing MK III");

        provider.add("block.filter_casing.tooltip", "Creates a §aParticle-Free§7 environment");
        provider.add("block.sterilizing_filter_casing.tooltip", "Creates a §aSterilized§7 environment");

        provider.add("block.gt6.explosive.breaking_tooltip",
                "Primes explosion when mined, sneak mine to pick back up");
        provider.add("block.gt6.explosive.lighting_tooltip", "Cannot be lit with Redstone");
        provider.add("block.gt6.powderbarrel.drops_tooltip",
                "Slightly larger than TNT, drops all destroyed Blocks as Items");
        provider.add("block.gt6.itnt.drops_tooltip", "Much larger than TNT, drops all destroyed Blocks as Items");

        provider.add("block.gt6.normal_laser_pipe.tooltip",
                "§7Transmitting power with §fno loss§7 in straight lines");
        provider.add("block.gt6.normal_optical_pipe.tooltip", "§7Transmitting §fComputation§7 or §fResearch Data§7");
    }
}
