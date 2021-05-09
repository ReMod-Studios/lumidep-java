package com.remodstudios.lumidep

import com.remodstudios.lumidep.block.LumidepBlocks
import com.remodstudios.lumidep.block.entity.LumidepBlockEntities
import com.remodstudios.lumidep.item.LumidepItems
import net.minecraft.util.Identifier

object Lumidep {
    const val MOD_ID = "lumidep"

    fun init() {
        LumidepItems.register()
        LumidepBlocks.register()
        LumidepBlockEntities.register()
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}