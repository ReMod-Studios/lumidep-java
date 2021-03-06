package io.github.remodstudios.lumidep.block

import io.github.remodstudios.lumidep.Lumidep
import io.github.remodstudios.remodcore.registry.BlockRegistryHelper
import net.minecraft.block.*
import net.minecraft.sound.BlockSoundGroup

@Suppress("UNUSED")
object LumidepBlocks: BlockRegistryHelper(Lumidep.MOD_ID) {
    val CLAMP_DECOY = addOfMaterial("clamp_decoy", Material.SOLID_ORGANIC, ::ClampDecoyBlock)
    val GIANT_PEARL_BLOCK = addOfMaterial("giant_pearl_block", Material.SOLID_ORGANIC) {
        Block(luminance { 15 }.emissiveLighting { _, _, _ -> true })
    }
    // /!\ NOT a FallingBlock
    val BLACK_SAND = addCopy("black_sand", Blocks.SAND) { Block(this) }
    val BLACK_SANDSTONE = addCopy("black_sandstone", Blocks.SANDSTONE)
    val CHISELED_BLACK_SANDSTONE = addCopy("chiseled_black_sandstone", Blocks.CHISELED_SANDSTONE)
    val CUT_BLACK_SANDSTONE = addCopy("cut_black_sandstone", Blocks.CUT_SANDSTONE)
    val SMOOTH_BLACK_SANDSTONE = addCopy("smooth_black_sandstone", Blocks.SMOOTH_SANDSTONE)

    // TODO: make it drop tungsten nuggets
    val TUNGSTEN_DEPOSIT = addCopyWithInit("tungsten_deposit", Blocks.SANDSTONE) { sounds(BlockSoundGroup.SAND) }
    val TUNGSTEN_BLOCK = addCopy("tungsten_block", Blocks.IRON_BLOCK)
    val GORGE_LUMP = addOfMaterial("gorge_lump", Material.SOLID_ORGANIC, ::GorgeLumpBlock)
    val ABSTAIN_MASS = addOfMaterial("abstain_mass", Material.SOLID_ORGANIC, ::GorgeLumpBlock)
    val LUMEROCK = addCopy("lumerock", Blocks.GLOWSTONE)

    // brackwood
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

    val BRACKWOOD_CAMPFIRE = addCopy("brackwood_campfire", Blocks.CAMPFIRE) { BrackwoodCampfireBlock(true, 1, this)}

    // TODO: how do we make this spread?
    val CORALLINE_ALGAE = addOfMaterial("coralline_algae", Material.REPLACEABLE_PLANT) { Block(strength(0.2f).sounds(BlockSoundGroup.VINE).nonOpaque()) }
    val KELPFRUIT_BLOCK = addCopy("kelpfruit_block", Blocks.KELP_PLANT)
}

