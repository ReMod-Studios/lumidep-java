package com.remodstudios.lumidep.client.render

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.block.Marking
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.util.SignType

@Environment(EnvType.CLIENT)
object SpriteIdentifierUtil {
    @JvmStatic fun markingSpriteId(marking: Marking)
            = SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, Lumidep.id("entity/signs/marking/${marking.asString()}"))

    @JvmStatic fun signSpriteId(signType: SignType)
            = SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, Lumidep.id("entity/signs/${signType.name}"))
}