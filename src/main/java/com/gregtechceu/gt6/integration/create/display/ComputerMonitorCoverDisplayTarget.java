package com.gregtechceu.gt6.integration.create.display;

import com.gregtechceu.gt6.api.capability.GTCapabilityHelper;
import com.gregtechceu.gt6.api.capability.ICoverable;
import com.gregtechceu.gt6.api.placeholder.IPlaceholderInfoProviderCover;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;

import com.simibubi.create.api.behaviour.display.DisplayTarget;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;

import java.util.List;

public class ComputerMonitorCoverDisplayTarget extends DisplayTarget {

    @Override
    public void acceptText(int line, List<MutableComponent> text, DisplayLinkContext context) {
        ICoverable coverable = GTCapabilityHelper.getCoverable(context.level(), context.getTargetPos(), Direction.DOWN);
        MutableComponent component = MutableComponent.create(ComponentContents.EMPTY);
        text.forEach(component::append);
        if (coverable != null) {
            for (Direction face : Direction.values()) {
                if (coverable.getCoverAtSide(face) instanceof IPlaceholderInfoProviderCover cover) {
                    cover.setDisplayTargetBufferLine(line, component);
                }
            }
        }
    }

    @Override
    public DisplayTargetStats provideStats(DisplayLinkContext context) {
        return new DisplayTargetStats(100, 1000, this);
    }
}
