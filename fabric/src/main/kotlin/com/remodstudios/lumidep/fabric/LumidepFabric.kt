package com.remodstudios.lumidep.fabric

import com.remodstudios.lumidep.Lumidep
import net.fabricmc.api.ModInitializer

object LumidepFabric: ModInitializer {
    override fun onInitialize() {
        Lumidep.init()
    }
}