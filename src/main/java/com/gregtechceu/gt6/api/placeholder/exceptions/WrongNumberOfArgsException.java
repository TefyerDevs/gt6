package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class WrongNumberOfArgsException extends PlaceholderException {

    public WrongNumberOfArgsException(int expected, int got) {
        super(Component.translatable("gt6.computer_monitor_cover.error.wrong_number_of_args", expected, got)
                .getString());
    }
}
