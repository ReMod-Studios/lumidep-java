package com.remodstudios.lumidep.item

import com.remodstudios.lumidep.misc.LumidepStats
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class KelpfruitPowderItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        user.increaseStat(LumidepStats.CONSUME_KELPFRUIT_POWDER, 1)

        return super.use(world, user, hand)
    }
}