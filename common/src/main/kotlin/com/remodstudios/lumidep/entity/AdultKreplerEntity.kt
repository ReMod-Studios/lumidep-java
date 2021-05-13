package com.remodstudios.lumidep.entity

import com.remodstudios.remodcore.ifTrueThenAlso
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.BreatheAirGoal
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.ai.pathing.SwimNavigation
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.MobEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.SoundEvents
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.core.PlayState
import software.bernie.geckolib3.core.controller.AnimationController
import software.bernie.geckolib3.core.event.predicate.AnimationEvent
import software.bernie.geckolib3.core.manager.AnimationData
import software.bernie.geckolib3.core.manager.AnimationFactory

// TODO no geckolib for now until they fix their BS - leocth
class AdultKreplerEntity(entityType: EntityType<AdultKreplerEntity>, world: World): OceanFloorWalkerEntity(entityType, world),
    IAnimatable {

    override fun getMaxAir() = 4800
    override fun getNextAirOnLand(air: Int) = maxAir
    override fun getActiveEyeHeight(entityPose: EntityPose, entityDimensions: EntityDimensions) = 0.3f
    override fun getLookPitchSpeed() = 1
    override fun getBodyYawSpeed() = 1

    override fun initialize(
        serverWorldAccess: ServerWorldAccess,
        localDifficulty: LocalDifficulty,
        spawnReason: SpawnReason,
        entityData: EntityData?,
        compoundTag: CompoundTag?
    ): EntityData? {
        air = maxAir
        pitch = 0f
        return super.initialize(serverWorldAccess, localDifficulty, spawnReason, entityData, compoundTag)
    }

    override fun initGoals() {
        super.initGoals()
        val thisMob = this@AdultKreplerEntity
        with(goalSelector) {
            add(0, BreatheAirGoal(thisMob))
            add(4, MeleeAttackGoal(thisMob, 1.0, true))
        }
    }

    override fun createNavigation(world: World) = SwimNavigation(this, world)

    override fun tryAttack(target: Entity)
        = target.damage(
            DamageSource.mob(this),
            getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toFloat()
        ).ifTrueThenAlso {
            dealDamage(this, target)
            playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1f, 1f)
        }


    companion object {
        fun createAttributes() = MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
    }

    override fun registerControllers(data: AnimationData) {
        data.addAnimationController(AnimationController(
            this, "controller", 0f,
            // the fact that i have to do this makes me angry
            object: AnimationController.IAnimationPredicate<AdultKreplerEntity> {
                override fun <P : IAnimatable?> test(p0: AnimationEvent<P>?)
                    = PlayState.CONTINUE
            }
        ))
    }

    override fun getFactory() = AnimationFactory(this)
}