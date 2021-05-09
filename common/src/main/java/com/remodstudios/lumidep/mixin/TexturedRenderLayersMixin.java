package com.remodstudios.lumidep.mixin;

import com.remodstudios.lumidep.block.BrackwoodSignType;
import com.remodstudios.lumidep.block.Marking;
import com.remodstudios.lumidep.client.render.SpriteIdentifierUtil;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "addDefaultTextures", at = @At("TAIL"))
    private static void addDefaultTextures(Consumer<SpriteIdentifier> adder, CallbackInfo ci) {
        adder.accept(SpriteIdentifierUtil.signSpriteId(BrackwoodSignType.INSTANCE));
        for (Marking marking : Marking.values()) {
            adder.accept(SpriteIdentifierUtil.markingSpriteId(marking));
        }
    }
}
