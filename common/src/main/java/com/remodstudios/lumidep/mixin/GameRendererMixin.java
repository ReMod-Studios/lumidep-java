package com.remodstudios.lumidep.mixin;

import com.remodstudios.lumidep.data.DoseCounterComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow private float movementFovMultiplier;
    @Shadow private float lastMovementFovMultiplier;

    // fuck it lets try this out

    /**
     * @author leocth
     */
    @Overwrite
    private void updateMovementFovMultiplier() {
        float speed = 1.0F;
        if (this.client.getCameraEntity() instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)this.client.getCameraEntity();
            speed = player.getSpeed();
            DoseCounterComponent component = DoseCounterComponent.get(player);
            double multiplier = 1.0 + component.getDramaticness() * client.options.fov * 0.007;
            //System.out.println(speed);
            //System.out.println(multiplier);
            //System.out.println(speed * multiplier);
            speed *= multiplier;
        }

        this.lastMovementFovMultiplier = this.movementFovMultiplier;
        this.movementFovMultiplier += (speed - this.movementFovMultiplier) * 0.5F;
        float fov = (float) client.options.fov;
        if (movementFovMultiplier * fov > MAX_FOV) {
            movementFovMultiplier *= MAX_FOV / fov;
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
