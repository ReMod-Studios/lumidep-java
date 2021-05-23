package com.remodstudios.lumidep.mixin;

import com.remodstudios.lumidep.data.DoseCounterComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow private float movementFovMultiplier;
    @Shadow private float lastMovementFovMultiplier;

    @Inject(
        method = "updateMovementFovMultiplier",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getSpeed()F",
            ordinal = 0,
            shift = At.Shift.AFTER
        ),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private void addDrugEffect(CallbackInfo ci, float speed, AbstractClientPlayerEntity player) {
        DoseCounterComponent component = DoseCounterComponent.get(player);
        if (component.getCurrentDose() > 0.0) {
            double fov = client.options.fov;
            double multiplier = MathHelper.lerp(component.getDramaticness(), 1.0, MAX_FOV / client.options.fov / speed - 0.2);

            this.lastMovementFovMultiplier = this.movementFovMultiplier;
            this.movementFovMultiplier = (float) (speed * multiplier);
            System.out.println(movementFovMultiplier);
            if (movementFovMultiplier * fov > MAX_FOV) {
                movementFovMultiplier *= MAX_FOV / fov;
            }
            ci.cancel();
        }
    }


    //TODO: make this configurable
    private static final float MAX_FOV = 170f;

    /*
    @Redirect(
        method = "updateMovementFovMultiplier",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getSpeed()F",
            ordinal = 0
        )
    )
    public float addDrugEffect(AbstractClientPlayerEntity player) {
        float speed = player.getSpeed();
        DoseCounterComponent component = DoseCounterComponent.get(player);
        float multiplier = 1f + (float)component.getCurrentDose() * 0.06f;
        //System.out.println(speed);
        //System.out.println(multiplier);
        //System.out.println(speed * multiplier);
        return speed * multiplier;
    }


    @Redirect(
        method = "updateMovementFovMultiplier",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/GameRenderer;movementFovMultiplier:F",
            opcode = Opcodes.PUTFIELD,
            ordinal = 1
        )
    )
    public void removeUpperBound(GameRenderer gameRenderer, float value) {
        float fov = (float) client.options.fov;
        System.out.println(fov);
        System.out.println(value);
        if (value * fov > MAX_FOV) {
            movementFovMultiplier = fov / MAX_FOV;
        } else {
            movementFovMultiplier = value;
        }
    }

     */
}
