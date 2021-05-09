package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.client.LumidepClient
import me.shedaniel.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Lumidep.MOD_ID)
object LumidepForge {
    init {
        EventBuses.registerModEventBus(Lumidep.MOD_ID, MOD_BUS);
        MOD_BUS.addListener(::onClientSetup)

        Lumidep.init();
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        LumidepClient.init()
    }
}