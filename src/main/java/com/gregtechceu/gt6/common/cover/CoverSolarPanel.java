package com.gregtechceu.gt6.common.cover;

import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.capability.GTCapabilityHelper;
import com.gregtechceu.gt6.api.capability.ICoverable;
import com.gregtechceu.gt6.api.capability.IEnergyContainer;
import com.gregtechceu.gt6.api.cover.CoverBehavior;
import com.gregtechceu.gt6.api.cover.CoverDefinition;
import com.gregtechceu.gt6.api.machine.TickableSubscription;
import com.gregtechceu.gt6.utils.GTUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class CoverSolarPanel extends CoverBehavior {

    private final long EUt;
    protected TickableSubscription subscription;

    public CoverSolarPanel(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
        this.EUt = 1;
    }

    public CoverSolarPanel(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide, int tier) {
        super(definition, coverHolder, attachedSide);
        this.EUt = GTValues.V[tier];
    }

    @Override
    public void onLoad() {
        super.onLoad();
        subscription = coverHolder.subscribeServerTick(subscription, this::update);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && attachedSide == Direction.UP && getEnergyContainer() != null;
    }

    protected void update() {
        Level level = coverHolder.getLevel();
        BlockPos blockPos = coverHolder.getPos();
        if (GTUtil.canSeeSunClearly(level, blockPos)) {
            IEnergyContainer energyContainer = getEnergyContainer();
            if (energyContainer != null) {
                energyContainer.acceptEnergyFromNetwork(null, EUt, 1);
            }
        }
    }

    @Nullable
    protected IEnergyContainer getEnergyContainer() {
        return GTCapabilityHelper.getEnergyContainer(coverHolder.getLevel(), coverHolder.getPos(), attachedSide);
    }
}
