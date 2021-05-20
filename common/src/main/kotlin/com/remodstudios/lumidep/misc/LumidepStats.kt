package com.remodstudios.lumidep.misc

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.remodcore.RegistryHelper
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.stat.StatFormatter
import net.minecraft.stat.Stats
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LumidepStats: RegistryHelper<Identifier>(
    DeferredRegister.create(Lumidep.MOD_ID, Registry.CUSTOM_STAT_KEY)
) {
    val CONSUME_KELPFRUIT_POWDER = add("consume_kelpfruit_powder", StatFormatter.DEFAULT)

    fun add(id: String, formatter: StatFormatter): Identifier {
        val ident = Lumidep.id(id)
        registry.register(ident) { ident }
        Stats.CUSTOM.getOrCreateStat(ident, formatter)
        return ident
    }
}