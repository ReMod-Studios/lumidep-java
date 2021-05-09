package com.remodstudios.lumidep.fabric

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.client.LumidepClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer

@Suppress("unused")
object LumidepFabric: ModInitializer {
    override fun onInitialize() {
        Lumidep.init()
    }
}

@Suppress("unused")
@Environment(EnvType.CLIENT)
object LumidepFabricClient: ClientModInitializer {
    override fun onInitializeClient() {
        LumidepClient.init()
    }
}