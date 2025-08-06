package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class NotEnoughArgsException extends PlaceholderException {

    public NotEnoughArgsException(int expected, int got) {
        super(Component.translatable("gt6.computer_monitor_cover.error.not_enough_args", expected, got).getString());
    }
}
