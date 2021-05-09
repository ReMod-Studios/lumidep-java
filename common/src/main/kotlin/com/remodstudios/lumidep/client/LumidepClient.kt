package com.remodstudios.lumidep.client

import com.remodstudios.lumidep.block.entity.LumidepBlockEntities
import com.remodstudios.lumidep.client.render.block.entity.BrackwoodSignBlockEntityRenderer
import me.shedaniel.architectury.registry.BlockEntityRenderers
import me.shedaniel.architectury.registry.RenderTypes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.RenderLayer
import com.remodstudios.lumidep.block.LumidepBlocks as Lb

@Environment(EnvType.CLIENT)
object LumidepClient {
    fun init() {
        registerBlockEntityRenderers()
        registerRenderLayers()
    }

    private fun registerBlockEntityRenderers() {
        BlockEntityRenderers.registerRenderer(LumidepBlockEntities.BRACKWOOD_SIGN, ::BrackwoodSignBlockEntityRenderer)
    }

    private fun registerRenderLayers() {
        RenderTypes.register(RenderLayer.getTranslucent(), Lb.CORALLINE_ALGAE)
        RenderTypes.register(RenderLayer.getCutout(), Lb.BRACKWOOD_DOOR, Lb.BRACKWOOD_TRAPDOOR)
    }
}