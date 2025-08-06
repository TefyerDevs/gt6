package com.gregtechceu.gt6.common.machine.multiblock.part;

import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.capability.IParallelHatch;
import com.gregtechceu.gt6.api.gui.widget.IntInputWidget;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;
import com.gregtechceu.gt6.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gt6.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gt6.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gt6.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gt6.api.machine.multiblock.part.TieredPartMachine;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.util.Mth;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class ParallelHatchPartMachine extends TieredPartMachine implements IFancyUIMachine, IParallelHatch {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            ParallelHatchPartMachine.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);
    private static final int MIN_PARALLEL = 1;

    private final int maxParallel;

    @Persisted
    @Getter
    private int currentParallel = 1;

    public ParallelHatchPartMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
        this.maxParallel = (int) Math.pow(4, tier - GTValues.EV);
    }

    public void setCurrentParallel(int parallelAmount) {
        this.currentParallel = Mth.clamp(parallelAmount, MIN_PARALLEL, this.maxParallel);
        for (IMultiController controller : this.getControllers()) {
            if (controller instanceof IRecipeLogicMachine rlm) {
                rlm.getRecipeLogic().markLastRecipeDirty();
            }
        }
    }

    @Override
    public Widget createUIWidget() {
        WidgetGroup parallelAmountGroup = new WidgetGroup(0, 0, 100, 20);
        parallelAmountGroup.addWidget(new IntInputWidget(this::getCurrentParallel, this::setCurrentParallel)
                .setMin(MIN_PARALLEL)
                .setMax(maxParallel));

        return parallelAmountGroup;
    }

    @Override
    @NotNull
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public boolean canShared() {
        return false;
    }
}
