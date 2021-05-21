package com.remodstudios.lumidep

import com.remodstudios.lumidep.block.LumidepBlocks
import com.remodstudios.lumidep.block.entity.LumidepBlockEntities
import com.remodstudios.lumidep.data.DoseCounterComponent
import com.remodstudios.lumidep.client.render.entity.LumidepEntities
import com.remodstudios.lumidep.item.LumidepItems
import com.remodstudios.lumidep.misc.OverdoseDamageSource
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

            println(counter.currentDose)
            println(counter.ticksSinceIntoxicated)
            if (counter.currentDose > 30.0) { // TODO make this configurable
                // wasted
                it.damage(OverdoseDamageSource, Float.MAX_VALUE)
            }
            println()
        }
    }

    fun id(path: String) = Identifier(MOD_ID, path)
}