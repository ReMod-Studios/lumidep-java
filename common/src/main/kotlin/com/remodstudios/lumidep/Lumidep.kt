package com.remodstudios.lumidep

import com.remodstudios.lumidep.block.LumidepBlocks
import com.remodstudios.lumidep.block.entity.LumidepBlockEntities
import com.remodstudios.lumidep.item.LumidepItems
import com.remodstudios.lumidep.worldgen.LumidepBiomes
import me.shedaniel.architectury.registry.BiomeModifications
import net.minecraft.util.Identifier

object Lumidep {
    const val MOD_ID = "lumidep"

    fun init() {
        LumidepItems.register()
        LumidepBlocks.register()
        LumidepBlockEntities.register()
        LumidepBiomes.register()
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}