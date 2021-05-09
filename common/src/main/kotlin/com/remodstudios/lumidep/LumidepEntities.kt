package com.remodstudios.lumidep

import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object LumidepEntities {
    private val REGISTRY = DeferredRegister.create(Lumidep.MOD_ID, Registry.ENTITY_TYPE_KEY)

    //val ADULT_KREPLER = add("adult_krepler", TODO)

    private fun <T: Entity> add(id: String, t: EntityType<T>) = REGISTRY.register(id) { t }
}

