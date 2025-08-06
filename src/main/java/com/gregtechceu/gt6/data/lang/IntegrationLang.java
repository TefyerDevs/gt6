package com.gregtechceu.gt6.data.lang;

import com.gregtechceu.gt6.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gt6.api.data.worldgen.bedrockfluid.BedrockFluidDefinition;
import com.gregtechceu.gt6.api.registry.GTRegistries;
import com.gregtechceu.gt6.common.data.GTBedrockFluids;
import com.gregtechceu.gt6.common.data.GTOres;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class IntegrationLang {

    public static void init(RegistrateLangProvider provider) {
        initRecipeViewerLang(provider);
        initWailaLikeLang(provider);
        initMinimapLang(provider);
        initOwnershipLang(provider);
    }

    /** JEI, REI, EMI */
    private static void initRecipeViewerLang(RegistrateLangProvider provider) {
        provider.add("gt6.jei.multiblock_info", "Multiblock Info");
        provider.add("gt6.jei.ore_processing_diagram", "Ore Processing Diagram");
        provider.add("gt6.jei.ore_vein_diagram", "Ore Vein Diagram");
        provider.add("gt6.jei.programmed_circuit", "Programmed Circuit Page");
        provider.add("gt6.jei.bedrock_fluid_diagram", "Bedrock Fluid Diagram");
        provider.add("gt6.jei.bedrock_ore_diagram", "Bedrock Ore Diagram");
        provider.add("gt6.jei.ore_vein_diagram.chance", "§eChance: %s§r");
        provider.add("gt6.jei.ore_vein_diagram.spawn_range", "Spawn Range:");
        provider.add("gt6.jei.ore_vein_diagram.weight", "Weight: %s");
        provider.add("gt6.jei.ore_vein_diagram.dimensions", "Dimensions:");
        GTRegistries.ORE_VEINS.unfreeze();
        GTOres.init();
        for (GTOreDefinition oreDefinition : GTRegistries.ORE_VEINS) {
            String name = GTRegistries.ORE_VEINS.getKey(oreDefinition).getPath();
            provider.add("gt6.jei.ore_vein." + name, RegistrateLangProvider.toEnglishName(name));
        }
        GTRegistries.BEDROCK_FLUID_DEFINITIONS.unfreeze();
        GTBedrockFluids.init();
        for (BedrockFluidDefinition fluid : GTRegistries.BEDROCK_FLUID_DEFINITIONS) {
            String name = GTRegistries.BEDROCK_FLUID_DEFINITIONS.getKey(fluid).getPath();
            provider.add("gt6.jei.bedrock_fluid." + name, RegistrateLangProvider.toEnglishName(name));
        }

        provider.add("gt6.rei.group.potion_fluids", "Potion Fluids");
    }

    /** Jade, TheOneProbe, WTHIT */
    private static void initWailaLikeLang(RegistrateLangProvider provider) {
        provider.add("gt6.top.working_disabled", "Working Disabled");
        provider.add("gt6.top.energy_consumption", "Using");
        provider.add("gt6.top.energy_production", "Producing");
        provider.add("gt6.top.transform_up", "§cStep Up§r %s");
        provider.add("gt6.top.transform_down", "§aStep Down§r %s");
        provider.add("gt6.top.transform_input", "§6Input:§r %s");
        provider.add("gt6.top.transform_output", "§9Output:§r %s");
        provider.add("gt6.top.convert_eu", "Converting §eEU§r -> §cFE§r");
        provider.add("gt6.top.convert_fe", "Converting §cFE§r -> §eEU§r");
        provider.add("gt6.top.fuel_min_consume", "Needs");
        provider.add("gt6.top.fuel_none", "No fuel");
        provider.add("gt6.top.invalid_structure", "Structure Incomplete");
        provider.add("gt6.top.valid_structure", "Structure Formed");
        provider.add("gt6.top.obstructed_structure", "Structure Obstructed");
        provider.add("gt6.top.maintenance_fixed", "Maintenance Fine");
        provider.add("gt6.top.maintenance_broken", "Needs Maintenance");
        provider.add("gt6.top.maintenance.wrench", "Pipe is loose");
        provider.add("gt6.top.maintenance.screwdriver", "Screws are loose");
        provider.add("gt6.top.maintenance.soft_mallet", "Something is stuck");
        provider.add("gt6.top.maintenance.hard_hammer", "Plating is dented");
        provider.add("gt6.top.maintenance.wire_cutter", "Wires burned out");
        provider.add("gt6.top.maintenance.crowbar", "That doesn't belong there");
        provider.add("gt6.top.primitive_pump_production", "Production: %s mB/s");
        provider.add("gt6.top.filter.label", "Filter:");
        provider.add("gt6.top.link_cover.color", "Color:");
        provider.add("gt6.top.mode.export", "Exporting");
        provider.add("gt6.top.mode.import", "Importing");
        provider.add("gt6.top.unit.items", "Items");
        provider.add("gt6.top.unit.fluid_milibuckets", "L");
        provider.add("gt6.top.unit.fluid_buckets", "kL");
        provider.add("gt6.top.recipe_output", "Recipe Outputs:");
        provider.add("gt6.top.item_auto_output", "Item Output: %s");
        provider.add("gt6.top.fluid_auto_output", "Fluid Output: %s");
        provider.add("gt6.top.auto_output", "Auto Output");
        provider.add("gt6.top.allow_output_input", "Allow Input");
        provider.add("gt6.top.cable_voltage", "Voltage: ");
        provider.add("gt6.top.cable_amperage", "Amperage: ");
        provider.add("gt6.top.exhaust_vent_direction", "Exhaust Vent: %s");
        provider.add("gt6.top.exhaust_vent_blocked", "Blocked");
        provider.add("gt6.top.machine_mode", "Machine Mode: ");
        provider.add("gt6.top.stained", "Colored: %s");
        provider.add("gt6.top.buffer_not_bound", "Buffer Not Currently Bound");
        provider.add("gt6.top.buffer_bound_pos", "Bound To - X: %s, Y: %s, Z: %s");
        provider.add("gt6.top.proxies_bound", "Buffer Proxies Bound: %s");

        provider.add("gt6.jade.energy_stored", "%d / %d EU");
        provider.add("gt6.jade.progress_computation", "%s / %s CWU");
        provider.add("gt6.jade.progress_sec", "%s / %s s");
        provider.add("gt6.jade.progress_tick", "%s / %s t");
        provider.add("gt6.jade.cleaned_this_second", "Cleaned hazard: %s/s");
        provider.add("gt6.jade.fluid_use", "%s mB/t");
        provider.add("gt6.jade.amperage_use", "%s A");
        provider.add("gt6.jade.at", " @ ");

        provider.add("gt6.top.energy_stored", " / %d EU");
        provider.add("gt6.top.progress_computation", " / %s CWU");
        provider.add("gt6.top.progress_sec", " / %s s");
        provider.add("gt6.top.progress_tick", " / %s t");
    }

    private static void initMinimapLang(RegistrateLangProvider provider) {
        provider.add("gt6.minimap.ore_vein.depleted", "Depleted");

        provider.add("message.gt6.new_veins.amount", "Prospected %d new veins!");
        provider.add("message.gt6.new_veins.name", "Prospected %s!");
        provider.add("button.gt6.mark_as_depleted.name", "Mark as Depleted");
        provider.add("button.gt6.toggle_waypoint.name", "Toggle Waypoint");

        provider.add("gt6.journeymap.options.layers", "Prospection layers");
        provider.add("gt6.journeymap.options.layers.ore_veins", "Show Ore Veins");
        provider.add("gt6.journeymap.options.layers.bedrock_fluids", "Show Bedrock Fluid Veins");
        provider.add("gt6.journeymap.options.layers.hide_depleted", "Hide Depleted Veins");
    }

    private static void initOwnershipLang(RegistrateLangProvider provider) {
        provider.add("gt6.ownership.name.player", "Player");
        provider.add("gt6.ownership.name.ftb", "FTB Teams");
        provider.add("gt6.ownership.name.argonauts", "Argonauts Guild");
    }
}
