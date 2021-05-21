package com.remodstudios.lumidep.item

import com.remodstudios.lumidep.data.DoseCounterComponent
import com.remodstudios.remodcore.applyStatusEffect
import net.minecraft.entity.effect.StatusEffects
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
        return if (!world.isClient) {
            if (!user.isCreative)
                stack.decrement(1)

            val counter = DoseCounterComponent.get(user)
            counter.currentDose += 5.0

            //user.increaseStat(LumidepStats.CONSUME_KELPFRUIT_POWDER, 1)

            user.applyStatusEffect(
                type = StatusEffects.SPEED,
                duration = 700,
                amplifier = 1
            )

            world.playSound(user, 0.0, 0.0, 0.0, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, 1.0f)

            TypedActionResult.success(stack)
        } else {
            TypedActionResult.pass(stack)
        }
    }
}