package com.remodstudios.lumidep.client

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.block.entity.LumidepBlockEntities
import com.remodstudios.lumidep.client.render.block.entity.BrackwoodSignBlockEntityRenderer
import com.remodstudios.lumidep.data.DoseCounterComponent
import com.remodstudios.remodcore.argb
import me.shedaniel.architectury.event.events.GuiEvent
import me.shedaniel.architectury.registry.BlockEntityRenderers
import me.shedaniel.architectury.registry.RenderTypes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.render.RenderLayer
import net.minecraft.util.math.MathHelper
import kotlin.math.pow
import com.remodstudios.lumidep.block.LumidepBlocks as Lb

@Environment(EnvType.CLIENT)
object LumidepClient {
    fun init() {
        registerBlockEntityRenderers()
        registerRenderLayers()

        GuiEvent.RENDER_HUD.register { matrices, delta ->
            val client = MinecraftClient.getInstance()
            val player = client.player ?: return@register

            val counter = DoseCounterComponent.get(player)

            if (counter.currentDose > 0.0) {
                val width = client.window.scaledWidth
                val height = client.window.scaledHeight

                // it's called the cubic lerp™
                val alpha = MathHelper.lerp(counter.dramaticness, 0.0, 130.0).toInt()
                DrawableHelper.fill(matrices, 0, 0, width, height, argb(alpha, 255, 0, 0))
            }
        }
    }

    private fun registerBlockEntityRenderers() {
        BlockEntityRenderers.registerRenderer(LumidepBlockEntities.BRACKWOOD_SIGN, ::BrackwoodSignBlockEntityRenderer)
    }

    private fun registerRenderLayers() {
        RenderTypes.register(RenderLayer.getTranslucent(), Lb.CORALLINE_ALGAE)
        RenderTypes.register(RenderLayer.getCutout(), Lb.BRACKWOOD_DOOR, Lb.BRACKWOOD_TRAPDOOR)
    }
}