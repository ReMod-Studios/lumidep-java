package com.remodstudios.lumidep.forge.client.render.entity

import com.remodstudios.lumidep.client.render.entity.NotAdultKreplerEntity
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.util.Identifier
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer

class AdultKreplerEntityRenderer(
    dispatcher: EntityRenderDispatcher
) : GeoEntityRenderer<NotAdultKreplerEntity>(dispatcher, AdultKreplerEntityModel) {

    init { shadowRadius = 0.7f }

    override fun getTexture(arg: NotAdultKreplerEntity) = AdultKreplerEntityModel.TEXTURE
}

object AdultKreplerEntityModel: BaseEntityModel<NotAdultKreplerEntity>("adult_krepler") {
    override fun getAnimationFileLocation(animatable: NotAdultKreplerEntity): Identifier? = null
}