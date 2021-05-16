package com.remodstudios.lumidep.fabric.client.render.entity

import com.remodstudios.lumidep.entity.AnglerfishEntity
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRenderDispatcher
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer

class AnglerfishEntityRenderer(
    dispatcher: EntityRenderDispatcher,
    ctx: EntityRendererRegistry.Context
) : GeoEntityRenderer<AnglerfishEntity>(dispatcher, AnglerFishEntityModel) {

    init { shadowRadius = 0.7f }

}

object AnglerFishEntityModel: BaseEntityModel<AnglerfishEntity>("anglerfish")