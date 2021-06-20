package io.github.remodstudios.lumidep.misc

import io.github.remodstudios.remodcore.registry.RegistryHelper
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.stat.StatFormatter
import net.minecraft.stat.Stats
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LumidepStats: RegistryHelper<Identifier>(
    DeferredRegister.create(io.github.remodstudios.lumidep.Lumidep.MOD_ID, Registry.CUSTOM_STAT_KEY)
) {
    val CONSUME_KELPFRUIT_POWDER = add("consume_kelpfruit_powder", StatFormatter.DEFAULT)

    fun add(id: String, formatter: StatFormatter): Identifier {
        val ident = io.github.remodstudios.lumidep.Lumidep.id(id)
        registry.register(ident) { ident }
        Stats.CUSTOM.getOrCreateStat(ident, formatter)
        return ident
    }
}