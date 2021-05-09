package com.remodstudios.lumidep.block.entity

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.remodcore.BlockEntityRegistryHelper
import com.remodstudios.remodcore.RegistryHelper
import me.shedaniel.architectury.registry.BlockEntityRenderers
import net.minecraft.block.entity.BlockEntityType
import com.remodstudios.lumidep.block.LumidepBlocks as Lb

object LumidepBlockEntities: BlockEntityRegistryHelper(Lumidep.MOD_ID) {
    val BRACKWOOD_SIGN = add(
        "brackwood_sign",
        ::BrackwoodSignBlockEntity,
        Lb.BRACKWOOD_SIGN, Lb.BRACKWOOD_WALL_SIGN
    )
}

