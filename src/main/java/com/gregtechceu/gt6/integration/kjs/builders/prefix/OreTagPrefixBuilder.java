package com.gregtechceu.gt6.integration.kjs.builders.prefix;

import com.gregtechceu.gt6.api.data.chemical.material.Material;
import com.gregtechceu.gt6.api.data.tag.TagPrefix;
import com.gregtechceu.gt6.common.data.GTBlocks;
import com.gregtechceu.gt6.core.mixins.BlockBehaviourAccessor;
import com.gregtechceu.gt6.integration.kjs.built.KJSTagPrefix;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Supplier;

import static com.gregtechceu.gt6.integration.kjs.Validator.*;

@Accessors(fluent = true, chain = true)
public class OreTagPrefixBuilder extends TagPrefixBuilder {

    @Setter
    public transient Supplier<BlockState> stateSupplier;
    @Setter
    public transient Supplier<Material> materialSupplier;
    @Setter
    public transient ResourceLocation baseModelLocation;
    @Setter
    public transient Supplier<BlockBehaviour.Properties> templateProperties;
    @Setter
    public transient boolean doubleDrops = false;
    @Setter
    public transient boolean isSand = false;
    @Setter
    public transient boolean shouldDropAsItem = false;

    public OreTagPrefixBuilder(ResourceLocation id) {
        super(id);
    }

    @Override
    public KJSTagPrefix create(String id) {
        return KJSTagPrefix.oreTagPrefix(id);
    }

    @Override
    public TagPrefix register() {
        validate(this.id,
                errorIfNull(stateSupplier, "stateSupplier"),
                onlySetDefault(templateProperties, () -> {
                    templateProperties = () -> GTBlocks.copy(
                            ((BlockBehaviourAccessor) stateSupplier.get().getBlock()).getBlockProperties(),
                            BlockBehaviour.Properties.of());
                }),
                errorIfNull(baseModelLocation, "baseModelLocation"));

        return value = base.registerOre(stateSupplier, materialSupplier, templateProperties, baseModelLocation,
                doubleDrops, isSand, shouldDropAsItem);
    }
}
