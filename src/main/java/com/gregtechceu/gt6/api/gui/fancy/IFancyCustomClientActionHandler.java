package com.gregtechceu.gt6.api.gui.fancy;

import net.minecraft.network.FriendlyByteBuf;

public interface IFancyCustomClientActionHandler {

    default void handleClientAction(int id, FriendlyByteBuf buffer) {}
}
