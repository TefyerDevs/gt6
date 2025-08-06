package com.gregtechceu.gt6.common.cover.detector;

import com.gregtechceu.gt6.api.capability.ICoverable;
import com.gregtechceu.gt6.api.cover.CoverDefinition;
import com.gregtechceu.gt6.utils.GTTransferUtils;
import com.gregtechceu.gt6.utils.RedstoneUtil;

import net.minecraft.core.Direction;
import net.minecraftforge.items.IItemHandler;

public class ItemDetectorCover extends DetectorCover {

    public ItemDetectorCover(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && getItemHandler() != null;
    }

    @Override
    protected void update() {
        if (this.coverHolder.getOffsetTimer() % 20 != 0)
            return;

        IItemHandler handler = getItemHandler();
        if (handler == null)
            return;

        int storedItems = 0;
        int itemCapacity = handler.getSlots() * handler.getSlotLimit(0);

        if (itemCapacity == 0)
            return;

        for (int i = 0; i < handler.getSlots(); i++) {
            storedItems += handler.getStackInSlot(i).getCount();
        }

        setRedstoneSignalOutput(RedstoneUtil.computeRedstoneValue(storedItems, itemCapacity, isInverted()));
    }

    protected IItemHandler getItemHandler() {
        return GTTransferUtils.getItemHandler(coverHolder.getLevel(), coverHolder.getPos(), attachedSide).resolve()
                .orElse(null);
    }
}
