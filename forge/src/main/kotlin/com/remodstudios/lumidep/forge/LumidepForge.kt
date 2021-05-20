package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.client.LumidepClient
import com.remodstudios.lumidep.client.render.entity.LumidepEntities
import com.remodstudios.lumidep.forge.client.render.entity.AdultKreplerEntityRenderer
import me.shedaniel.architectury.platform.forge.EventBuses
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Lumidep.MOD_ID)
object LumidepForge {
    init {
        EventBuses.registerModEventBus(Lumidep.MOD_ID, MOD_BUS);
        MOD_BUS.addListener(::onCommonSetup)
        MOD_BUS.addListener(::onClientSetup)

        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun onCommonSetup(event: FMLCommonSetupEvent) {
        Lumidep.init();
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        LumidepClient.init()
        RenderingRegistry.registerEntityRenderingHandler(LumidepEntities.ADULT_KREPLER, ::AdultKreplerEntityRenderer)
    }
}