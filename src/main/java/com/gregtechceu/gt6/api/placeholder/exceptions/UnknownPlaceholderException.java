package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class UnknownPlaceholderException extends PlaceholderException {

    public UnknownPlaceholderException(String name) {
        super(Component.translatable("gt6.computer_monitor_cover.error.no_placeholder", name).getString());
    }
}
