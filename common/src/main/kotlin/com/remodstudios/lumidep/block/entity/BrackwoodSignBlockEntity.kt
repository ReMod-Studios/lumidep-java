package com.remodstudios.lumidep.block.entity

import com.remodstudios.lumidep.LumidepBlocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType

class BrackwoodSignBlockEntity: BlockEntity(TYPE) {


    companion object {
        val TYPE: BlockEntityType<BrackwoodSignBlockEntity> = // no self types? seriously?
            BlockEntityType.Builder.create(
                ::BrackwoodSignBlockEntity,
                LumidepBlocks.BRACKWOOD_SIGN,
                LumidepBlocks.BRACKWOOD_WALL_SIGN
            ).build(null)
    }
}