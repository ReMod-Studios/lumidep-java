package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.item.LumidepItems
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.DefaultAttributeRegistry
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

@Suppress("unused")
object LumidepPlatformInterfaceImpl {
    @JvmStatic fun getItemGroup(): ItemGroup {
        return object : ItemGroup("lumidep.group") {
            override fun createIcon(): ItemStack {
                return ItemStack(LumidepItems.ESCA_LURE)
            }
        }
    }

    @JvmStatic
    fun registerEntityAttributes(entityType: EntityType<out LivingEntity>,
                                 attributeBuilder: DefaultAttributeContainer.Builder) {
        //TODO use EntityAttributeCreationEvent, might need a lot of refactoring
        DefaultAttributeRegistry.put(entityType, attributeBuilder.build());
    }
}