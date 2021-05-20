package com.remodstudios.lumidep.data.fabric

import com.remodstudios.lumidep.Lumidep
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy

object Components: EntityComponentInitializer {
    val DOSE_COUNTER =
        ComponentRegistryV3.INSTANCE.getOrCreate(Lumidep.id("dose_counter"), DoseCounterComponentImpl::class.java)

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerForPlayers(DOSE_COUNTER, { DoseCounterComponentImpl() }, RespawnCopyStrategy.LOSSLESS_ONLY)
    }
}