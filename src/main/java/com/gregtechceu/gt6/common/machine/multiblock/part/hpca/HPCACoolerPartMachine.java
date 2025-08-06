package com.gregtechceu.gt6.common.machine.multiblock.part.hpca;

import com.gregtechceu.gt6.api.GTValues;
import com.gregtechceu.gt6.api.capability.IHPCACoolantProvider;
import com.gregtechceu.gt6.api.gui.GuiTextures;
import com.gregtechceu.gt6.api.machine.IMachineBlockEntity;

import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

import net.minecraft.MethodsReturnNonnullByDefault;

import lombok.Getter;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HPCACoolerPartMachine extends HPCAComponentPartMachine implements IHPCACoolantProvider {

    @Getter
    private final boolean advanced;

    public HPCACoolerPartMachine(IMachineBlockEntity holder, boolean advanced) {
        super(holder);
        this.advanced = advanced;
    }

    @Override
    public ResourceTexture getComponentIcon() {
        return advanced ? GuiTextures.HPCA_ICON_ACTIVE_COOLER_COMPONENT : GuiTextures.HPCA_ICON_HEAT_SINK_COMPONENT;
    }

    @Override
    public int getUpkeepEUt() {
        return advanced ? GTValues.VA[GTValues.IV] : 0;
    }

    @Override
    public boolean canBeDamaged() {
        return false;
    }

    @Override
    public int getCoolingAmount() {
        return advanced ? 2 : 1;
    }

    @Override
    public boolean isActiveCooler() {
        return advanced;
    }

    @Override
    public int getMaxCoolantPerTick() {
        return advanced ? 8 : 0;
    }
}
