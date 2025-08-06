package com.gregtechceu.gt6.data;

import com.gregtechceu.gt6.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gt6.common.registry.GTRegistration;
import com.gregtechceu.gt6.core.mixins.registrate.RegistrateDataProviderAccessor;
import com.gregtechceu.gt6.data.lang.LangHandler;
import com.gregtechceu.gt6.data.model.BlockstateModelLoader;
import com.gregtechceu.gt6.data.tags.*;

import com.tterrag.registrate.providers.ProviderType;

public class GregTechDatagen {

    // we only register this so the class gets loaded. the key gets overwritten in #initPre.
    private static final ProviderType<GTBlockstateProvider> BLOCKSTATE_PROVIDER = ProviderType.register("ex_blockstate",
            GTBlockstateProvider::new);

    public static void initPre() {
        // replace some default providers with ours
        RegistrateDataProviderAccessor.gt6$getTypes().forcePut("blockstate", BLOCKSTATE_PROVIDER);

        GTRegistration.REGISTRATE.addDataGenerator(ProviderType.BLOCKSTATE,
                p -> BlockstateModelLoader.init((GTBlockstateProvider) p));
    }

    public static void initPost() {
        GTRegistration.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, BlockTagLoader::init);
        GTRegistration.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ItemTagLoader::init);
        GTRegistration.REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, FluidTagLoader::init);
        GTRegistration.REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, EntityTypeTagLoader::init);
        GTRegistration.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
