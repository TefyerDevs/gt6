package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class UnclosedBracketException extends PlaceholderException {

    public UnclosedBracketException() {
        super(Component.translatable("gt6.computer_monitor_cover.error.unclosed_bracket").getString());
    }
}
