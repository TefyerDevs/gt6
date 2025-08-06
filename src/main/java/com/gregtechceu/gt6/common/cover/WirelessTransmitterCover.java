package com.gregtechceu.gt6.common.cover;

import com.gregtechceu.gt6.api.capability.ICoverable;
import com.gregtechceu.gt6.api.cover.CoverBehavior;
import com.gregtechceu.gt6.api.cover.CoverDefinition;
import com.gregtechceu.gt6.api.machine.feature.IDataStickInteractable;
import com.gregtechceu.gt6.api.placeholder.IPlaceholderInfoProviderCover;

import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class WirelessTransmitterCover extends CoverBehavior
                                      implements IDataStickInteractable, IPlaceholderInfoProviderCover {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            WirelessTransmitterCover.class, CoverBehavior.MANAGED_FIELD_HOLDER);

    @Getter
    private final List<MutableComponent> createDisplayTargetBuffer = new ArrayList<>();

    public WirelessTransmitterCover(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
        for (int i = 0; i < 100; i++) createDisplayTargetBuffer.add(MutableComponent.create(ComponentContents.EMPTY));
    }

    @Override
    public InteractionResult onDataStickUse(Player player, ItemStack dataStick) {
        dataStick.getOrCreateTag().putInt("targetX", coverHolder.getPos().getX());
        dataStick.getOrCreateTag().putInt("targetY", coverHolder.getPos().getY());
        dataStick.getOrCreateTag().putInt("targetZ", coverHolder.getPos().getZ());
        dataStick.getOrCreateTag().putString("face", attachedSide.getName());
        return InteractionResult.SUCCESS;
    }

    @Override
    public long getTicksSincePlaced() {
        return coverHolder.getOffsetTimer();
    }

    @Override
    public void setDisplayTargetBufferLine(int line, MutableComponent component) {
        createDisplayTargetBuffer.set(line, component);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
