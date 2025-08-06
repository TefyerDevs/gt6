package com.gregtechceu.gt6.core.mixins;

import com.gregtechceu.gt6.api.capability.GTCapabilityHelper;
import com.gregtechceu.gt6.api.capability.IMedicalConditionTracker;
import com.gregtechceu.gt6.config.ConfigHolder;
import com.gregtechceu.gt6.core.IFireImmuneEntity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements IFireImmuneEntity {

    @Shadow
    public abstract EntityType<?> getType();

    @Unique
    private boolean gt6$fireImmune = false;
    @Unique
    private boolean gt6$isEntityInit = false;

    @ModifyReturnValue(method = "fireImmune", at = @At("RETURN"))
    private boolean gt6$changeFireImmune(boolean original) {
        return original || gt6$fireImmune;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gt6$onEntityInit(EntityType<?> entityType, Level level, CallbackInfo ci) {
        gt6$isEntityInit = true;
    }

    public void gt6$setFireImmune(boolean isImmune) {
        this.gt6$fireImmune = isImmune;
    }

    @ModifyReturnValue(method = "getMaxAirSupply", at = @At("RETURN"))
    private int gt6$hazardModifyMaxAir(int original) {
        if (!gt6$isEntityInit) return original;
        if (!ConfigHolder.INSTANCE.gameplay.hazardsEnabled) return original;

        IMedicalConditionTracker tracker = GTCapabilityHelper.getMedicalConditionTracker((Entity) (Object) this);
        if (tracker != null && tracker.getMaxAirSupply() != -1) {
            return tracker.getMaxAirSupply();
        }
        return original;
    }
}
