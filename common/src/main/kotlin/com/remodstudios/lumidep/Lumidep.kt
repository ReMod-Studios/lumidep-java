package com.remodstudios.lumidep

import net.minecraft.util.Identifier

object Lumidep {
    const val MOD_ID = "lumidep"

    fun init() {
        LumidepItems.register()
        LumidepBlocks.register()
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}