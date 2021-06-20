package io.github.remodstudios.lumidep.fabric

import io.github.remodstudios.lumidep.Lumidep.id
import io.github.remodstudios.lumidep.data.fabric.Components
import io.github.remodstudios.lumidep.item.LumidepItems
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

@Suppress("unused")
object LumidepPlatformInterfaceImpl {
    @JvmStatic
    fun registerEntityAttributes(entityType: EntityType<out LivingEntity>,
                                 attributeBuilder: DefaultAttributeContainer.Builder) {
        FabricDefaultAttributeRegistry.register(entityType, attributeBuilder)
    }

    @JvmStatic
    fun getDose(user: PlayerEntity): Double = Components.DOSE_COUNTER[user].currentDose
}