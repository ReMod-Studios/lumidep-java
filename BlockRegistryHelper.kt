package com.remodstudios.remodcore

import com.remodstudios.lumidep.LumidepBlocks
import me.shedaniel.architectury.registry.BlockProperties
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.registry.Registry

open class BlockRegistryHelper(
    registry: DeferredRegister<Block>
): RegistryHelper<Block>(registry) {

    constructor(modid: String)
            : this(DeferredRegister.create(modid, Registry.BLOCK_KEY))

    fun <V: Block> add(
        id: String,
        v: V,
    ): V {
        registry.register(id) { v }
        return v
    }

    fun <V: Block> addOfProp(
        id: String,
        prop: BlockProperties,
        factory: BlockProperties.() -> V,
    ): V {
        val block = prop.factory()
        return LumidepBlocks.add(id, block)
    }

    fun <Original: Block, V: Block> addCopyWithFactory(
        id: String,
        original: Original,
        factory: BlockProperties.() -> V,
    ): V {
        return addOfProp(id, BlockProperties.copy(original), factory)
    }

    fun <Original: Block> addCopy(
        id: String,
        original: Original,
        init: BlockProperties.() -> Unit,
    ): Block {
        val prop = BlockProperties.copy(original)
        prop.init()
        return addOfProp(id, prop, ::Block)
    }

    fun <Original: Block> addCopy(
        id: String,
        original: Original
    ): Block {
        return addCopy(id, original, ::Block)
    }

    fun <V: Block> addOfMaterial(
        id: String,
        mat: Material,
        factory: BlockProperties.() -> V
    ): V {
        return addOfProp(id, BlockProperties.of(mat), factory)
    }

    fun <V: Block> addWoodlike(
        id: String,
        factory: BlockProperties.() -> V
    ): V {
        return addOfMaterial(id, Material.WOOD) {
            strength(2.0F)
            sounds(BlockSoundGroup.WOOD)
            this.factory()
        }
    }
}