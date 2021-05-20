package com.remodstudios.lumidep.fabric

import com.remodstudios.lumidep.Lumidep.id
import com.remodstudios.lumidep.data.fabric.Components
import com.remodstudios.lumidep.item.LumidepItems
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
    @JvmStatic fun getItemGroup(): ItemGroup {
        return FabricItemGroupBuilder.build(
            id("group")
        ) { ItemStack(LumidepItems.ESCA_LURE) }
    }

    @JvmStatic
    fun registerEntityAttributes(entityType: EntityType<out LivingEntity>,
                                 attributeBuilder: DefaultAttributeContainer.Builder) {
        FabricDefaultAttributeRegistry.register(entityType, attributeBuilder)
    }

    @JvmStatic
    fun getDose(user: PlayerEntity): Double = Components.DOSE_COUNTER[user].currentDose

    @JvmStatic
    fun setDose(user: PlayerEntity, value: Double) { Components.DOSE_COUNTER[user].currentDose = value }
}