package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.client.LumidepClient
import com.remodstudios.lumidep.entity.LumidepEntities
import com.remodstudios.lumidep.forge.client.render.entity.AdultKreplerEntityRenderer
import com.remodstudios.lumidep.item.LumidepItems
import me.shedaniel.architectury.platform.forge.EventBuses
import net.minecraft.entity.attribute.DefaultAttributeRegistry
import net.minecraft.item.Items
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
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

    @SubscribeEvent
    private fun onFurnaceFuelBurnTimeEvent(event: FurnaceFuelBurnTimeEvent) {
        // ugly workaround
        if (event.itemStack.item == LumidepItems.TUNGSTEN_CARBON) {
            event.burnTime = 1920 // 1.2x coal
            event.result = Event.Result.ALLOW
        }
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        LumidepClient.init()
        RenderingRegistry.registerEntityRenderingHandler(LumidepEntities.ADULT_KREPLER, ::AdultKreplerEntityRenderer)
    }
}