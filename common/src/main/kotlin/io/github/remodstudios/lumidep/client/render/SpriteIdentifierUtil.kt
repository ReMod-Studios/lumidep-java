package io.github.remodstudios.lumidep.client.render

import io.github.remodstudios.lumidep.Lumidep
import io.github.remodstudios.lumidep.block.Marking
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.util.SignType

@Environment(EnvType.CLIENT)
object SpriteIdentifierUtil {
    @JvmStatic fun markingSpriteId(marking: Marking)
            = SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, io.github.remodstudios.lumidep.Lumidep.id("entity/signs/marking/${marking.asString()}"))

    @JvmStatic fun signSpriteId(signType: SignType)
            = SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, io.github.remodstudios.lumidep.Lumidep.id("entity/signs/${signType.name}"))
}