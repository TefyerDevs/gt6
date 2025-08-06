package com.gregtechceu.gt6.common.block;

import com.gregtechceu.gt6.api.block.IFilterType;
import com.gregtechceu.gt6.api.machine.multiblock.CleanroomType;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum CleanroomFilterType implements IFilterType {

    FILTER_CASING("filter_casing", CleanroomType.CLEANROOM),
    FILTER_CASING_STERILE("sterilizing_filter_casing", CleanroomType.STERILE_CLEANROOM);

    private final String name;
    @Getter
    private final CleanroomType cleanroomType;

    CleanroomFilterType(String name, CleanroomType cleanroomType) {
        this.name = name;
        this.cleanroomType = cleanroomType;
    }

    @NotNull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    @NotNull
    @Override
    public String toString() {
        return getSerializedName();
    }
}
