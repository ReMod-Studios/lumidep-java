package io.github.remodstudios.lumidep.block

import io.github.remodstudios.lumidep.block.entity.BrackwoodSignBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SignBlock
import net.minecraft.block.WallSignBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.SignType
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

object BrackwoodSignType: SignType("brackwood")

class BrackwoodSignBlock(settings: Settings) : SignBlock(settings, BrackwoodSignType) {
    // delegated
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        user: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult = Shared.onUse(state, world, pos, user, hand, hit)

    override fun createBlockEntity(blockView: BlockView): BlockEntity = Shared.createBlockEntity(blockView)
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        Shared.appendProperties(builder)
    }
}

class BrackwoodWallSignBlock(settings: Settings) : WallSignBlock(settings, BrackwoodSignType) {
    // delegated
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        user: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult = Shared.onUse(state, world, pos, user, hand, hit)

    override fun createBlockEntity(blockView: BlockView): BlockEntity = Shared.createBlockEntity(blockView)
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        Shared.appendProperties(builder)
    }
}

// - Mom, can we have multi-inheritance?
// - We already have multi-inheritance at home.
// Multi-inheritance at home:

// The reason that this is sensible is that the vast majority of Minecraft `Block`s
// hold no state - it is basically a glorified `object` ('cos flyweight) - so `this` doesn't
// matter for the most of the logic. - leocth

object Shared {
    //val MARKING =

    internal fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        user: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        // TODO
        return ActionResult.PASS
    }
    internal fun createBlockEntity(blockView: BlockView): BlockEntity = BrackwoodSignBlockEntity()

    internal fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(Marking.PROPERTY)
    }
}

enum class Marking(private val id: String): StringIdentifiable {
    TEST("test"),
    TEST2("test2"),
    ;

    companion object {
        val PROPERTY: EnumProperty<Marking> =
            EnumProperty.of("marking", Marking::class.java)
    }

    override fun asString(): String = id
    override fun toString(): String = id
}
