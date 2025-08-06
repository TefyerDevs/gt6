package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class InvalidArgsException extends PlaceholderException {

    public InvalidArgsException() {
        super(Component.translatable("gt6.computer_monitor_cover.error.invalid_args").getString());
    }
}
