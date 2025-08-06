package com.gregtechceu.gt6.api.pipenet;

import net.minecraft.resources.ResourceLocation;

public interface IPipeType<NodeDataType> {

    /**
     * the thickness of the pipe.
     */
    float getThickness();

    /**
     * modify the node data by the pipe type.
     */
    NodeDataType modifyProperties(NodeDataType baseProperties);

    /**
     * can the pipe be painted as other color.
     */
    boolean isPaintable();

    /**
     * indicate a unique type id.
     */
    ResourceLocation type();
}
