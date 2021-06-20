package io.github.remodstudios.lumidep.forge

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.DefaultAttributeRegistry

@Suppress("unused")
object LumidepPlatformInterfaceImpl {
    @JvmStatic
    fun registerEntityAttributes(entityType: EntityType<out LivingEntity>,
                                 attributeBuilder: DefaultAttributeContainer.Builder) {
        //TODO use EntityAttributeCreationEvent, might need a lot of refactoring
        DefaultAttributeRegistry.put(entityType, attributeBuilder.build());
    }
}