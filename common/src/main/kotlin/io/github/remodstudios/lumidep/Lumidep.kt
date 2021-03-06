package io.github.remodstudios.lumidep

import io.github.remodstudios.lumidep.block.LumidepBlocks
import io.github.remodstudios.lumidep.block.entity.LumidepBlockEntities
import io.github.remodstudios.lumidep.client.render.entity.LumidepEntities
import io.github.remodstudios.lumidep.data.DoseCounterComponent
import io.github.remodstudios.lumidep.item.LumidepItems
import io.github.remodstudios.lumidep.misc.OverdoseDamageSource
import me.shedaniel.architectury.event.events.TickEvent
import me.shedaniel.architectury.registry.fuel.FuelRegistry
import net.minecraft.util.Identifier

object Lumidep {
    const val MOD_ID = "lumidep"

    fun init() {
        LumidepEntities.register()
        LumidepItems.register()
        LumidepBlocks.register()
        LumidepBlockEntities.register()
        //LumidepStats.register()
        //LumidepBiomes.register()

        // TODO: make this configurable?
        FuelRegistry.register(1920, LumidepItems.TUNGSTEN_CARBON)

        TickEvent.PLAYER_PRE.register {
            val counter = DoseCounterComponent.get(it)

            if (counter.currentDose > DoseCounterComponent.LETHAL_DOSE) { // TODO make this configurable
                // wasted
                it.damage(OverdoseDamageSource, Float.MAX_VALUE)
            }
        }
    }

    fun id(path: String) = Identifier(io.github.remodstudios.lumidep.Lumidep.MOD_ID, path)
}