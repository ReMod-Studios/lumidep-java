package com.remodstudios.lumidep.fabric

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.client.LumidepClient
import com.remodstudios.lumidep.entity.LumidepEntities
import com.remodstudios.lumidep.fabric.client.render.entity.AdultKreplerEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry

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
        with(EntityRendererRegistry.INSTANCE) {
            register(LumidepEntities.ADULT_KREPLER, ::AdultKreplerEntityRenderer)
        }
    }
}