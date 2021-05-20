package com.remodstudios.lumidep.client.render.entity.fabric

import com.remodstudios.lumidep.client.render.entity.AdultKreplerEntity
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.util.Identifier
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer

class AdultKreplerEntityRenderer(
    dispatcher: EntityRenderDispatcher,
    ctx: EntityRendererRegistry.Context
) : GeoEntityRenderer<AdultKreplerEntity>(dispatcher, AdultKreplerEntityModel) {

    init { shadowRadius = 0.7f }

}

object AdultKreplerEntityModel: BaseEntityModel<AdultKreplerEntity>("adult_krepler") {
    override fun getAnimationFileLocation(animatable: AdultKreplerEntity): Identifier? = null
}