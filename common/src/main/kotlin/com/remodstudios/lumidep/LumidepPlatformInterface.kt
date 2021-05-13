@file:JvmName("LumidepPlatformInterface")
package com.remodstudios.lumidep

import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.item.ItemGroup

@ExpectPlatform
fun getItemGroup(): ItemGroup = throw AssertionError()

@ExpectPlatform
fun registerEntityAttributes(entityType: EntityType<out LivingEntity>,
                             attributeBuilder: DefaultAttributeContainer.Builder) {
    throw AssertionError()
}