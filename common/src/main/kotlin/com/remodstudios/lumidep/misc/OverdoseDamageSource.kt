package com.remodstudios.lumidep.misc

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

object OverdoseDamageSource: DamageSource("overdose") {
    init {
        setUsesMagic()
        setBypassesArmor()
        setUnblockable()
    }

    override fun getDeathMessage(entity: LivingEntity): Text {
        return TranslatableText("death.lumidep.overdose", entity.displayName)
    }
}