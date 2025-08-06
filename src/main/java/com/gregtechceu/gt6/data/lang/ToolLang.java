package com.gregtechceu.gt6.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class ToolLang {

    public static void init(RegistrateLangProvider provider) {
        initDeathMessages(provider);
        initToolInfo(provider);
    }

    private static void initDeathMessages(RegistrateLangProvider provider) {
        provider.add("death.attack.gt6.heat", "%s was boiled alive");
        provider.add("death.attack.gt6.frost", "%s explored cryogenics");
        provider.add("death.attack.gt6.chemical", "%s had a chemical accident");
        provider.add("death.attack.gt6.electric", "%s was electrocuted");
        provider.add("death.attack.gt6.radiation", "%s glows with joy now");
        provider.add("death.attack.gt6.turbine", "%s put their head into a turbine");
        provider.add("death.attack.gt6.explosion", "%s exploded");
        provider.add("death.attack.gt6.explosion.player", "%s exploded with help of %s");
        provider.add("death.attack.gt6.heat.player", "%s was boiled alive by %s");
        provider.add("death.attack.gt6.pickaxe", "%s got mined by %s");
        provider.add("death.attack.gt6.shovel", "%s got dug up by %s");
        provider.add("death.attack.gt6.axe", "%s has been chopped by %s");
        provider.add("death.attack.gt6.hoe", "%s had their head tilled by %s");
        provider.add("death.attack.gt6.hammer", "%s was squashed by %s");
        provider.add("death.attack.gt6.mallet", "%s got hammered to death by %s");
        provider.add("death.attack.gt6.mining_hammer", "%s was mistaken for Ore by %s");
        provider.add("death.attack.gt6.spade", "%s got excavated by %s");
        provider.add("death.attack.gt6.wrench", "%s gave %s a whack with the Wrench!");
        provider.add("death.attack.gt6.file", "%s has been filed D for 'Dead' by %s");
        provider.add("death.attack.gt6.crowbar", "%s lost half a life to %s");
        provider.add("death.attack.gt6.screwdriver", "%s has screwed with %s for the last time!");
        provider.add("death.attack.gt6.mortar", "%s was ground to dust by %s");
        provider.add("death.attack.gt6.wire_cutter", "%s has cut the cable for the Life Support Machine of %s");
        provider.add("death.attack.gt6.scythe", "%s had their soul taken by %s");
        provider.add("death.attack.gt6.knife", "%s was gently poked by %s");
        provider.add("death.attack.gt6.butchery_knife", "%s was butchered by %s");
        provider.add("death.attack.gt6.drill_lv", "%s was drilled with 32V by %s");
        provider.add("death.attack.gt6.drill_mv", "%s was drilled with 128V by %s");
        provider.add("death.attack.gt6.drill_hv", "%s was drilled with 512V by %s");
        provider.add("death.attack.gt6.drill_ev", "%s was drilled with 2048V by %s");
        provider.add("death.attack.gt6.drill_iv", "%s was drilled with 8192V by %s");
        provider.add("death.attack.gt6.chainsaw_lv", "%s was massacred by %s");
        provider.add("death.attack.gt6.wrench_lv", "%s's pipes were loosened by %s");
        provider.add("death.attack.gt6.wrench_hv", "%s's pipes were loosened by %s");
        provider.add("death.attack.gt6.wrench_iv", "%s had a Monkey Wrench thrown into their plans by %s");
        provider.add("death.attack.gt6.buzzsaw", "%s got buzzed by %s");
        provider.add("death.attack.gt6.screwdriver_lv", "%s had their screws removed by %s");

        provider.add("death.attack.gt6.medical_condition/asbestosis", "%s got mesothelioma");
        provider.add("death.attack.gt6.medical_condition/chemical_burns", "%s had a chemical accident");
        provider.add("death.attack.gt6.medical_condition/poison",
                "%s forgot that poisonous materials are, in fact, poisonous");
        provider.add("death.attack.gt6.medical_condition/silicosis",
                "%s didn't die of tuberculosis. it was silicosis.");
        provider.add("death.attack.gt6.medical_condition/arsenicosis", "%s got arsenic poisoning");
        provider.add("death.attack.gt6.medical_condition/berylliosis", "%s mined emeralds a bit too greedily");
        provider.add("death.attack.gt6.medical_condition/carcinogen", "%s got leukemia");
        provider.add("death.attack.gt6.medical_condition/irritant", "%s got a §n§lREALLY§r bad rash");
        provider.add("death.attack.gt6.medical_condition/methanol_poisoning",
                "%s tried to drink moonshine during the prohibition");
        provider.add("death.attack.gt6.medical_condition/nausea", "%s died of nausea");
        provider.add("death.attack.gt6.medical_condition/none", "%s died of... nothing?");
        provider.add("death.attack.gt6.medical_condition/weak_poison", "%s ate lead (or mercury!)");
        provider.add("death.attack.gt6.medical_condition/carbon_monoxide_poisoning", "%s left the stove on");
    }

    private static void initToolInfo(RegistrateLangProvider provider) {}
}
