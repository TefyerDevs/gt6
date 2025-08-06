package com.gregtechceu.gt6.utils.memoization;

import com.gregtechceu.gt6.api.data.chemical.material.stack.MaterialEntry;

import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * A variant of the memoized supplier that stores a block explicitly.
 * Use this to save blocks to
 * {@link com.gregtechceu.gt6.api.data.chemical.material.ItemMaterialData#registerMaterialEntry(Supplier, MaterialEntry)}}
 */
public class MemoizedBlockSupplier<T extends Block> extends MemoizedSupplier<T> {

    protected MemoizedBlockSupplier(Supplier<T> delegate) {
        super(delegate);
    }
}
