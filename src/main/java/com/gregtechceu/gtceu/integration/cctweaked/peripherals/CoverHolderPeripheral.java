package com.gregtechceu.gtceu.integration.cctweaked.peripherals;

import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.placeholder.IPlaceholderInfoProviderCover;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.GenericPeripheral;

public class CoverHolderPeripheral implements GenericPeripheral {

    @Override
    public String id() {
        return "gtceu:coverable";
    }

    @LuaFunction
    public static MethodResult setBufferedText(ICoverable coverable, String face, int line, String text) {
        Direction direction = Direction.byName(face);
        if (direction == null) return MethodResult.of(false, "invalid face");
        if (line < 1 || line > 100) return MethodResult.of(false, "line must be from 1 to 100 (inclusive)");
        if (coverable.getCoverAtSide(direction) instanceof IPlaceholderInfoProviderCover cover) {
            cover.setDisplayTargetBufferLine(line, Component.literal(text));
            return MethodResult.of(true, "success");
        } else return MethodResult.of(false, "invalid cover");
    }
}
