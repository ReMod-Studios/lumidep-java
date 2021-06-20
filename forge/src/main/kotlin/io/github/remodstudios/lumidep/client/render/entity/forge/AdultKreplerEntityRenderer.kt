package io.github.remodstudios.lumidep.client.render.entity.forge

import io.github.remodstudios.lumidep.client.render.entity.AdultKreplerEntity
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.util.Identifier
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer

class AdultKreplerEntityRenderer(
    dispatcher: EntityRenderDispatcher
) : GeoEntityRenderer<AdultKreplerEntity>(dispatcher, AdultKreplerEntityModel) {

    init { shadowRadius = 0.7f }

    override fun getTexture(arg: AdultKreplerEntity) = AdultKreplerEntityModel.TEXTURE
}

object AdultKreplerEntityModel: BaseEntityModel<AdultKreplerEntity>("adult_krepler") {
    override fun getAnimationFileLocation(animatable: AdultKreplerEntity): Identifier? = null
}