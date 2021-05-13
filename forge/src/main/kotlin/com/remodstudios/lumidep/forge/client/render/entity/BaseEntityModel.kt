package com.remodstudios.lumidep.forge.client.render.entity

import com.remodstudios.lumidep.Lumidep
import net.minecraft.util.Identifier
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.model.AnimatedGeoModel

open class BaseEntityModel<T : IAnimatable>(id: String): AnimatedGeoModel<T>() {
    val MODEL = Lumidep.id("geo/$id.geo.json")
    val TEXTURE = Lumidep.id("textures/entity/$id.png")
    val ANIMATION = Lumidep.id("animations/$id.animation.json")


    override fun getModelLocation(entity: T): Identifier? = MODEL
    override fun getTextureLocation(entity: T): Identifier? = TEXTURE
    override fun getAnimationFileLocation(animatable: T): Identifier? = ANIMATION
}