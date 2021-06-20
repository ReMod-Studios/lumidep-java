package io.github.remodstudios.lumidep.client.render.entity.fabric

import io.github.remodstudios.lumidep.Lumidep
import net.minecraft.util.Identifier
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.model.AnimatedGeoModel

open class BaseEntityModel<T : IAnimatable>(id: String): AnimatedGeoModel<T>() {
    val MODEL = io.github.remodstudios.lumidep.Lumidep.id("geo/$id.geo.json")
    val TEXTURE = io.github.remodstudios.lumidep.Lumidep.id("textures/entity/$id.png")
    val ANIMATION = io.github.remodstudios.lumidep.Lumidep.id("animations/$id.animation.json")


    override fun getModelLocation(entity: T): Identifier? = MODEL
    override fun getTextureLocation(entity: T): Identifier? = TEXTURE
    override fun getAnimationFileLocation(animatable: T): Identifier? = ANIMATION
}