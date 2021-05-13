package com.remodstudios.lumidep.entity

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.registerEntityAttributes
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

object LumidepEntities {
    private val REGISTRY = DeferredRegister.create(Lumidep.MOD_ID, Registry.ENTITY_TYPE_KEY)

    val ADULT_KREPLER = addLiving<AdultKreplerEntity>("adult_krepler") {
        type {
            spawnGroup = SpawnGroup.WATER_CREATURE
            factory(::AdultKreplerEntity)
            config {
                setDimensions(2.5f, 4.0f)
            }
            attributes = AdultKreplerEntity.createAttributes()
        }
    }

    fun register() { REGISTRY.register() }

    private fun <T: Entity> add(id: String, t: EntityType<T>)
        = t.also { REGISTRY.register(id) { it } }

    private inline fun <T: Entity> add(
        id: String,
        init: EntityTypeScope<T>.() -> Unit
    ) = add(id, EntityTypeScope<T>(id).apply(init).build())


    private inline fun <T: LivingEntity> addLiving(
        id: String,
        init: LivingEntityTypeScope<T>.() -> Unit
    ) = add(id, LivingEntityTypeScope<T>(id).apply(init).build())
}

open class EntityTypeScope<T: Entity>(val id: String) {
    private lateinit var entityTypeBuilder: EntityType.Builder<T>

    fun type(init: Type.() -> Unit) {
        entityTypeBuilder = Type().apply(init).build()
    }

    open fun build(): EntityType<T> = entityTypeBuilder.build(id)

    inner class Type {
        lateinit var spawnGroup: SpawnGroup
        var factory: EntityType.EntityFactory<T> = EntityType.EntityFactory { _, _ -> null }
        var config: EntityType.Builder<T>.() -> Unit = {}

        fun factory(factory: (EntityType<T>, World) -> T) {
            this.factory = EntityType.EntityFactory { entityType, world -> factory(entityType, world) }
        }

        fun factory(factory: EntityType.EntityFactory<T>) {
            this.factory = factory
        }

        fun config(config: EntityType.Builder<T>.() -> Unit) {
            this.config = config
        }

        fun build() = EntityType.Builder.create(factory, spawnGroup).apply(config)
    }
}

class LivingEntityTypeScope<T: LivingEntity>(id: String): EntityTypeScope<T>(id) {
    var attributes: DefaultAttributeContainer.Builder? = null

    override fun build(): EntityType<T> {
        val type = super.build()
        attributes?.let { registerEntityAttributes(type, it) }
        return type
    }
}