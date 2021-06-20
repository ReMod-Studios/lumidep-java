package io.github.remodstudios.lumidep.mixin;

import io.github.remodstudios.lumidep.data.DoseCounterComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
        method = "getMovementSpeed()F",
        at = @At("HEAD"),
        cancellable = true
    )
    private void sprintConstantly(CallbackInfoReturnable<Float> cir) {
        //noinspection ConstantConditions
        DoseCounterComponent counter = DoseCounterComponent.get((PlayerEntity)(Object)this);

        if (counter.getCurrentDose() > 0.0) {
            double mul = MathHelper.lerp(counter.getDramaticness(), 1.0, 2.5);
            cir.setReturnValue((float) (this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * mul));
        }
    }
}
