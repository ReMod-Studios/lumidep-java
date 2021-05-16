package com.remodstudios.lumidep.entity

import com.remodstudios.remodcore.ifTrueThenAlso
import com.remodstudios.remodcore.times
import com.remodstudios.remodcore.toRad
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.SwimNavigation
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.GuardianEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.WaterCreatureEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.core.PlayState
import software.bernie.geckolib3.core.controller.AnimationController
import software.bernie.geckolib3.core.event.predicate.AnimationEvent
import software.bernie.geckolib3.core.manager.AnimationData
import software.bernie.geckolib3.core.manager.AnimationFactory

class AnglerfishEntity(entityType: EntityType<AnglerfishEntity>, world: World): WaterCreatureEntity(entityType, world), IAnimatable {

    init {
        moveControl = AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, true)
        lookControl = AquaticLookControl(this, 10)
        setCanPickUpLoot(true)
    }

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
        val thisMob = this@AnglerfishEntity
        goalSelector.add(0, BreatheAirGoal(thisMob))
        goalSelector.add(0, MoveIntoWaterGoal(thisMob))

        goalSelector.add(3, FleeEntityGoal(thisMob, GuardianEntity::class.java, 8f, 1.0, 1.0))
        //goalSelector.add(3, FleeEntityGoal(thisMob, GoblinSharkEntity::class.java, 8f, 1.0, 1.0))

        goalSelector.add(4, SwimAroundGoal(thisMob, 1.0, 10))
        goalSelector.add(4, MeleeAttackGoal(thisMob, 1.2, true))
        goalSelector.add(4, LookAroundGoal(thisMob))

        goalSelector.add(5, LookAtEntityGoal(thisMob, PlayerEntity::class.java, 6f))
        goalSelector.add(8, ChaseBoatGoal(thisMob))

        targetSelector.add(1, RevengeGoal(thisMob, GuardianEntity::class.java).setGroupRevenge())
    }

    override fun createNavigation(world: World) = SwimNavigation(this, world)

    override fun tick() {
        super.tick()
        if (isAiDisabled) {
            // don't choke yourself
            air = maxAir
            return
        }

        if (onGround) {
            // jump around and shriek like crazy, like guardians
            val randomX = (random.nextFloat() * 2f - 1f) * 0.2 // [-0.2,0.2]
            val randomZ = (random.nextFloat() * 2f - 1f) * 0.2 // [-0.2,0.2]
            addVelocity(randomX, 0.5, randomZ)
            yaw = random.nextFloat() * 360f // random yaw
            onGround = false
        }

        if (world.isClient && isTouchingWater && velocity.lengthSquared() > 0.03) {
            val rotationVec = getRotationVec(0f)
            val xOffset = MathHelper.cos(yaw.toRad()) * 0.3f
            val zOffset = MathHelper.sin(yaw.toRad()) * 0.3f

            val scale = 1.2f - random.nextFloat() * 0.7f // [0.5,1.2]

            val particleX = x - rotationVec.x * scale
            val particleY = y - rotationVec.y
            val particleZ = x - rotationVec.z * scale
            for (i in 0..2) {
                world.addParticle(ParticleTypes.DOLPHIN, particleX + xOffset, particleY, particleZ + zOffset, 0.0, 0.0, 0.0)
                world.addParticle(ParticleTypes.DOLPHIN, particleX - xOffset, particleY, particleZ - zOffset, 0.0, 0.0, 0.0)
            }
        }
    }

    override fun tryAttack(target: Entity)
        = target.damage(
            DamageSource.mob(this),
            getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toFloat()
        ).ifTrueThenAlso {
            dealDamage(this, target)
            playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1f, 1f)
        }

    override fun travel(input: Vec3d) {
        if (canMoveVoluntarily() && isTouchingWater) {
            updateVelocity(movementSpeed, input)
            move(MovementType.SELF, velocity)
            velocity *= 0.9
            if (target == null)
                addVelocity(0.0, -0.005, 0.0)
        } else {
            super.travel(input)
        }
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
            PredicateWrapper { PlayState.CONTINUE }
        ))
    }

    override fun getFactory() = AnimationFactory(this)
}

typealias Pred<T> = (AnimationEvent<T>) -> PlayState

@JvmInline
value class PredicateWrapper<T: IAnimatable>(val pred: Pred<*>): AnimationController.IAnimationPredicate<T> {
    override fun <P : IAnimatable> test(event: AnimationEvent<P>): PlayState = pred(event)
}

class LessBadAnimationController