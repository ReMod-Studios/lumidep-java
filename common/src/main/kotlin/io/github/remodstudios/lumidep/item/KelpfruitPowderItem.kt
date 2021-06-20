package io.github.remodstudios.lumidep.item

import io.github.remodstudios.lumidep.data.DoseCounterComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class KelpfruitPowderItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        if (!world.isClient) {
            if (!user.isCreative)
                stack.decrement(1)

            val counter = DoseCounterComponent.get(user)
            counter.consume(5.0)

            // prevent the user from consuming it all at once
            user.itemCooldownManager[this] = 15

            //user.increaseStat(LumidepStats.CONSUME_KELPFRUIT_POWDER, 1)

            world.playSound(null, user.x, user.y, user.z, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.5f, 1.0f)
        }
        return TypedActionResult.success(stack)
    }


}