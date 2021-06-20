package io.github.remodstudios.lumidep.forge

import io.github.remodstudios.lumidep.Lumidep
import io.github.remodstudios.lumidep.client.LumidepClient
import io.github.remodstudios.lumidep.client.render.entity.LumidepEntities
import io.github.remodstudios.lumidep.client.render.entity.forge.AdultKreplerEntityRenderer
import io.github.remodstudios.lumidep.data.DoseCounterComponent
import io.github.remodstudios.lumidep.data.forge.DoseCounterComponentImpl
import io.github.remodstudios.lumidep.data.forge.DoseCounterProvider
import io.github.remodstudios.lumidep.data.forge.DoseCounterStorage
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

@Mod(io.github.remodstudios.lumidep.Lumidep.MOD_ID)
object LumidepForge {
    init {
        EventBuses.registerModEventBus(io.github.remodstudios.lumidep.Lumidep.MOD_ID, MOD_BUS);
        MOD_BUS.addListener(::onCommonSetup)
        MOD_BUS.addListener(::onClientSetup)

        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun onCommonSetup(event: FMLCommonSetupEvent) {
        event.enqueueWork {
            io.github.remodstudios.lumidep.Lumidep.init();
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
        when (event.`object`) {
            is PlayerEntity -> event.addCapability(io.github.remodstudios.lumidep.Lumidep.id("dose_counter"), DoseCounterProvider())
        }
    }

    @SubscribeEvent
    fun onPlayerTick(event: TickEvent.PlayerTickEvent) {
        val capability = event.player.getCapability(DoseCounterProvider.DOSE_COUNTER)
        capability.ifPresent {
            //println("before: dose = ${it.currentDose}, ticks = ${it.ticksSinceIntoxicated}")
            it.tick()
            //println("after: dose = ${it.currentDose}, ticks = ${it.ticksSinceIntoxicated}")
            //println()
        }
    }
}