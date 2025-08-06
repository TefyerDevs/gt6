package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class InvalidNumberException extends PlaceholderException {

    public InvalidNumberException(String number) {
        super(Component.translatable("gt6.computer_monitor_cover.error.invalid_number", number).getString());
    }
}
