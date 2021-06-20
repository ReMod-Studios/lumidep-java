package io.github.remodstudios.lumidep.fabric

import io.github.remodstudios.lumidep.Lumidep
import io.github.remodstudios.lumidep.client.LumidepClient
import io.github.remodstudios.lumidep.client.render.entity.LumidepEntities
import io.github.remodstudios.lumidep.client.render.entity.fabric.AdultKreplerEntityRenderer
import io.github.remodstudios.lumidep.client.render.entity.fabric.AnglerfishEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry

@Suppress("unused")
object LumidepFabric: ModInitializer {
    override fun onInitialize() {
        io.github.remodstudios.lumidep.Lumidep.init()
    }
}

@Suppress("unused")
@Environment(EnvType.CLIENT)
object LumidepFabricClient: ClientModInitializer {
    override fun onInitializeClient() {
        LumidepClient.init()
        with(EntityRendererRegistry.INSTANCE) {
            register(LumidepEntities.ADULT_KREPLER, ::AdultKreplerEntityRenderer)
            register(LumidepEntities.ANGLERFISH, ::AnglerfishEntityRenderer)
        }
    }
}