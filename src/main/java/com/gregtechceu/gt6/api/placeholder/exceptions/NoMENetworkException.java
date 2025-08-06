package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class NoMENetworkException extends PlaceholderException {

    public NoMENetworkException() {
        super(Component.translatable("gt6.computer_monitor_cover.error.no_ae").getString());
    }
}
