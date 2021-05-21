package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.client.LumidepClient
import com.remodstudios.lumidep.client.render.entity.LumidepEntities
import com.remodstudios.lumidep.client.render.entity.forge.AdultKreplerEntityRenderer
import com.remodstudios.lumidep.data.DoseCounterComponent
import com.remodstudios.lumidep.data.forge.DoseCounterComponentImpl
import com.remodstudios.lumidep.data.forge.DoseCounterProvider
import com.remodstudios.lumidep.data.forge.DoseCounterStorage
import me.shedaniel.architectury.platform.forge.EventBuses
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
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
        event.enqueueWork {
            Lumidep.init();
            CapabilityManager.INSTANCE.register(DoseCounterComponent::class.java, DoseCounterStorage(), ::DoseCounterComponentImpl)
        }
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            LumidepClient.init()
            RenderingRegistry.registerEntityRenderingHandler(LumidepEntities.ADULT_KREPLER, ::AdultKreplerEntityRenderer)
        }
    }

    @SubscribeEvent
    fun onAttachCapabilities(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` !is PlayerEntity) return
        event.addCapability(Lumidep.id("dose_counter"), DoseCounterProvider())
    }

    @SubscribeEvent
    fun onPlayerTick(event: TickEvent.PlayerTickEvent) {
        val capability = event.player.getCapability(DoseCounterProvider.DOSE_COUNTER)
        capability.ifPresent {
            println("before: dose = ${it.currentDose}, ticks = ${it.ticksSinceIntoxicated}")
            it.tickLogic()
            println("after: dose = ${it.currentDose}, ticks = ${it.ticksSinceIntoxicated}")
            println()
        }
    }
}