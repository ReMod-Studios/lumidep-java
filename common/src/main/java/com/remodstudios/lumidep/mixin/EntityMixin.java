package com.remodstudios.lumidep.mixin;

import com.remodstudios.lumidep.data.DoseCounterComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(
        method = "isSprinting",
        at = @At("HEAD"),
        cancellable = true)
    private void sprintConstantly(CallbackInfoReturnable<Boolean> cir) {
        Object iCanBeAnything = this;
        if (iCanBeAnything instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) iCanBeAnything;
            DoseCounterComponent counter = DoseCounterComponent.get(player);
            if (counter.getCurrentDose() > 0.0) cir.setReturnValue(true);
        }
    }
}
