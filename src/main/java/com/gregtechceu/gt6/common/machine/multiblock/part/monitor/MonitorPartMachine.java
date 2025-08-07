package com.gregtechceu.gt6.common.machine.multiblock.part.monitor;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class MonitorPartMachine extends MonitorComponentPartMachine {

    public MonitorPartMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean isMonitor() {
        return true;
    }

    @Override
    public IGuiTexture getComponentIcon() {
        return ResourceTexture.fromSpirit(Gregtech.id("item/computer_monitor_cover"));
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }
}
