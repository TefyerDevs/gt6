package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class UnexpectedBracketException extends RuntimeException {

    public UnexpectedBracketException() {
        super(Component.translatable("gt6.computer_monitor_cover.error.unexpected_bracket").getString());
    }
}
