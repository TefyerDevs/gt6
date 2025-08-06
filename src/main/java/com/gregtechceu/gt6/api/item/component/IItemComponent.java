package com.gregtechceu.gt6.api.item.component;

import com.gregtechceu.gt6.api.item.ComponentItem;

import net.minecraft.world.item.Item;

/**
 * Describes generic component attachable to {@link ComponentItem}
 * Multiple components can be attached to one item
 */
public interface IItemComponent {

    default void onAttached(Item item) {}
}
