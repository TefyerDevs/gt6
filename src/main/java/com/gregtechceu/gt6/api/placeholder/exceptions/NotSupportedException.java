package com.gregtechceu.gt6.api.placeholder.exceptions;

import net.minecraft.network.chat.Component;

public class NotSupportedException extends PlaceholderException {

    public NotSupportedException() {
        super(Component.translatable("gt6.computer_monitor_cover.error.not_supported").getString());
    }
}
