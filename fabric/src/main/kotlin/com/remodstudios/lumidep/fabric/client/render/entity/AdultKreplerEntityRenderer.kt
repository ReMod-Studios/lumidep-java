package com.remodstudios.lumidep.fabric.client.render.entity

import com.remodstudios.lumidep.entity.NotAdultKreplerEntity
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.util.Identifier
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer

class AdultKreplerEntityRenderer(
    dispatcher: EntityRenderDispatcher,
    ctx: EntityRendererRegistry.Context
) : GeoEntityRenderer<NotAdultKreplerEntity>(dispatcher, AdultKreplerEntityModel) {

    init { shadowRadius = 0.7f }

}

object AdultKreplerEntityModel: BaseEntityModel<NotAdultKreplerEntity>("adult_krepler") {
    override fun getAnimationFileLocation(animatable: NotAdultKreplerEntity): Identifier? = null
}