package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.Lumidep
import me.shedaniel.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Lumidep.MOD_ID)
object LumidepForge {
    init {
        EventBuses.registerModEventBus(Lumidep.MOD_ID, MOD_BUS);
        Lumidep.init();
    }
}