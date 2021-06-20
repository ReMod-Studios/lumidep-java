package io.github.remodstudios.lumidep.client.render.block.entity

import io.github.remodstudios.lumidep.block.Marking
import io.github.remodstudios.lumidep.block.entity.BrackwoodSignBlockEntity
import io.github.remodstudios.lumidep.client.render.SpriteIdentifierUtil
import io.github.remodstudios.lumidep.misc.frame
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.AbstractSignBlock
import net.minecraft.block.SignBlock
import net.minecraft.block.WallSignBlock
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f

@Environment(EnvType.CLIENT)
class BrackwoodSignBlockEntityRenderer(dispatcher: BlockEntityRenderDispatcher)
    : BlockEntityRenderer<BrackwoodSignBlockEntity>(dispatcher) {

    private var signModel = SignBlockEntityRenderer.SignModel()

    override fun render(
        blockEntity: BrackwoodSignBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        light: Int, overlay: Int
    ) {
        val state = blockEntity.cachedState
        val signBlock = state.block as AbstractSignBlock

        val marking = state[Marking.PROPERTY]
        val markingSpriteId = SpriteIdentifierUtil.markingSpriteId(marking);
        val signSpriteId = SpriteIdentifierUtil.signSpriteId(signBlock.signType)

        matrices.frame {
            it.translate(0.5, 0.5, 0.5)
            when (state.block) {
                is SignBlock -> {
                    val rot = -state[SignBlock.ROTATION] * 360 / 16.0f
                    it.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(rot))
                    signModel.foot.visible = true
                }
                is WallSignBlock -> {
                    val rot = -state[WallSignBlock.FACING].asRotation()
                    it.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(rot))
                    it.translate(0.0, -0.3125, -0.4375)
                    signModel.foot.visible = false
                }
                else -> {
                    throw IllegalStateException("block should be either a SignBlock or a WallSignBlock")
                }
            }

            it.scale(2/3f, -2/3f, -2/3f)
            val signVertexConsumer = signSpriteId.getVertexConsumer(vertexConsumerProvider, signModel::getLayer)
            val markingVertexConsumer = markingSpriteId.getVertexConsumer(vertexConsumerProvider, signModel::getLayer)
            signModel.foot.render(matrices, signVertexConsumer, light, overlay)
            signModel.field.render(matrices, signVertexConsumer, light, overlay)
            signModel.field.render(matrices, markingVertexConsumer, light, overlay)
        }

    }

}