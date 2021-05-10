package com.remodstudios.lumidep.block

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.remodcore.BlockRegistryHelper
import net.minecraft.block.*
import net.minecraft.sound.BlockSoundGroup


object LumidepBlocks: BlockRegistryHelper(Lumidep.MOD_ID) {
    val BLACK_SAND = addCopy("black_sand", Blocks.DIRT) { FallingBlock(sounds(BlockSoundGroup.SAND)) }
    val TUNGSTEN_DEPOSIT = addCopyWithInit("tungsten_deposit", Blocks.SANDSTONE) { sounds(BlockSoundGroup.SAND) }
    val TUNGSTEN_BLOCK = addCopy("tungsten_block", Blocks.IRON_BLOCK)
    val CORALLINE_ALGAE = addOfMaterial("coralline_algae", Material.REPLACEABLE_PLANT) { Block(strength(0.2f).sounds(BlockSoundGroup.VINE).nonOpaque()) }
    val DEAD_KELP = addCopy("dead_kelp", Blocks.KELP_PLANT)
    val LUMEROCK = addCopy("lumerock", Blocks.GLOWSTONE)

    val BRACKWOOD_LOG = addWoodlike("brackwood_log", ::PillarBlock)
    val STRIPPED_BRACKWOOD_LOG = addWoodlike("stripped_brackwood_log", ::PillarBlock)
    val BRACKWOOD_WOOD = addWoodlike("brackwood_wood", ::PillarBlock)
    val STRIPPED_BRACKWOOD_WOOD = addWoodlike("stripped_brackwood_wood", ::PillarBlock)

    val BRACKWOOD_PLANKS = addCopy("brackwood_planks", Blocks.OAK_PLANKS)
    val BRACKWOOD_PRESSURE_PLATE = addCopy("brackwood_pressure_plate", Blocks.OAK_PRESSURE_PLATE)
    { OpenPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, this) }

    val BRACKWOOD_BUTTON = addCopy("brackwood_button", Blocks.OAK_BUTTON, ::OpenWoodenButtonBlock)
    val BRACKWOOD_DOOR = addCopy("brackwood_door", Blocks.OAK_DOOR, ::OpenDoorBlock)
    val BRACKWOOD_TRAPDOOR = addCopy("brackwood_trapdoor", Blocks.OAK_TRAPDOOR, ::OpenTrapdoorBlock)
    val BRACKWOOD_SIGN = addCopy("brackwood_sign", Blocks.OAK_SIGN, ::BrackwoodSignBlock)
    val BRACKWOOD_WALL_SIGN = addCopy("brackwood_wall_sign", Blocks.OAK_SIGN, ::BrackwoodWallSignBlock)
    val BRACKWOOD_SLAB = addCopy("brackwood_slab", Blocks.OAK_SLAB, ::SlabBlock)
    val BRACKWOOD_STAIRS = addCopy("brackwood_stairs", Blocks.OAK_STAIRS) { OpenStairsBlock(BRACKWOOD_PLANKS.defaultState, this) }

    val BRACKWOOD_FENCE = addCopy("brackwood_fence", Blocks.OAK_FENCE, ::FenceBlock)
    val BRACKWOOD_FENCE_GATE = addCopy("brackwood_fence_gate", Blocks.OAK_FENCE_GATE, ::FenceGateBlock)


    fun lol() {

    }
}

