package io.github.remodstudios.lumidep.block.entity

import io.github.remodstudios.lumidep.Lumidep.MOD_ID
import io.github.remodstudios.remodcore.registry.BlockEntityRegistryHelper
import io.github.remodstudios.lumidep.block.LumidepBlocks as Lb

object LumidepBlockEntities: BlockEntityRegistryHelper(MOD_ID) {
    val BRACKWOOD_SIGN = add(
        "brackwood_sign",
        ::BrackwoodSignBlockEntity,
        Lb.BRACKWOOD_SIGN, Lb.BRACKWOOD_WALL_SIGN
    )
}

