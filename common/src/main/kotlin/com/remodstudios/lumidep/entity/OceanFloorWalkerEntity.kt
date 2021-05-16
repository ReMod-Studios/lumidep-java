package com.remodstudios.lumidep.entity

import com.remodstudios.remodcore.*
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.MovementType
import net.minecraft.entity.ai.TargetFinder
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.MobNavigation
import net.minecraft.entity.ai.pathing.PathNodeType
import net.minecraft.entity.ai.pathing.SwimNavigation
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.GuardianEntity
import net.minecraft.entity.mob.WaterCreatureEntity
import net.minecraft.entity.mob.ZombifiedPiglinEntity
import net.minecraft.entity.passive.TurtleEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.WorldView
import java.util.*
import kotlin.math.PI

open class OceanFloorWalkerEntity(entityType: EntityType<out WaterCreatureEntity>, world: World) : WaterCreatureEntity(entityType, world) {

    protected val waterNavigation by lazy { SwimNavigation(this, world) }
    protected val landNavigation by lazy { MobNavigation(this, world) }

    private val hasFinishedCurrentPath: Boolean
        get() {
            val path = navigation.currentPath ?: return false
            val pos = path.target ?: return false
            return squaredDistanceTo(pos) < 4.0
        }

    private var _isTargetingUnderwater = false

    private var isTargetingUnderwater
        get() = _isTargetingUnderwater || (target?.isTouchingWater == true)
        set(value) { _isTargetingUnderwater = value }

    override fun canFly() = !isSwimming

    init { init() }

    protected fun init() {
        moveControl = OceanFloorMoveControl()
        setPathfindingPenalty(PathNodeType.WATER, 0f)
    }

    override fun initGoals() {
        //with(goalSelector) {
        goalSelector.add(1, WanderAroundOnSurfaceGoal(1.0))
        goalSelector.add(5, LeaveWaterGoal(1.0))
        goalSelector.add(6, TargetAboveWaterGoal(1.0, world.seaLevel))
        goalSelector.add(7, WanderAroundGoal(thisMob, 1.0))
        //}
        //with(targetSelector) {
        goalSelector.add(1, RevengeGoal(thisMob,
                OceanFloorWalkerEntity::class.java,
                GuardianEntity::class.java,
                /* GorgeBeastEntity::class.java, TODO */
            ).setGroupRevenge(ZombifiedPiglinEntity::class.java))
        goalSelector.add(2, FollowTargetGoal(thisMob, PlayerEntity::class.java, true, false))
        goalSelector.add(3, FollowTargetGoal(thisMob, TurtleEntity::class.java, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER))
        //}
    }

    override fun travel(input: Vec3d) {
        if (canMoveVoluntarily() && isTouchingWater && isTargetingUnderwater) {
            updateVelocity(0.01f, input)
            move(MovementType.SELF, velocity)
            velocity = velocity.multiply(0.9)
        } else {
            super.travel(input)
        }
    }

    override fun updateSwimming() {
        if (!world.isClient) {
            isSwimming = if (this.canMoveVoluntarily() && this.isTouchingWater && this.isTargetingUnderwater) {
                navigation = waterNavigation
                true
            } else {
                navigation = landNavigation
                false
            }
        }
    }

    override fun tick() {
        super.tick()
        if (this.isAiDisabled) {
            this.air = this.maxAir // don't suffocate the damn thing
        }
    }

    private val thisMob
        get() = this@OceanFloorWalkerEntity

    inner class OceanFloorMoveControl: MoveControl(this) {
        override fun tick() {
            val target = thisMob.target
            if (thisMob.isTargetingUnderwater && thisMob.isTouchingWater) {
                // if target is above the mob, ascend
                // (maybe 0.002 is too low, though)
                if (target != null && target.y > thisMob.y || thisMob.isTargetingUnderwater) {
                    thisMob.addVelocity(0.0, 0.002, 0.0)
                }

                // if we're not moving and idle, stop moving at all
                if (state != State.MOVE_TO || thisMob.navigation.isIdle) {
                    thisMob.movementSpeed = 0f
                    return
                }

                // difference between the mob and the target
                val dx = targetX - thisMob.x
                var dy = targetY - thisMob.x
                val dz = targetZ - thisMob.x
                val mag = magnitude(dx, dy, dz)
                dy /= mag // wait what?

                // calculate the facing angle
                val toYaw = MathHelper.atan2(dz, dx).toDeg().toFloat() - 90f
                thisMob.yaw = changeAngle(thisMob.yaw, toYaw, 90f)
                thisMob.bodyYaw = thisMob.yaw

                val movementSpeed = speed * thisMob.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                // constant delta :concern:
                val cutoffSpeed = MathHelper.lerp(0.125f, getMovementSpeed(), movementSpeed.toFloat())
                thisMob.movementSpeed = cutoffSpeed
                thisMob.addVelocity(cutoffSpeed * dx * 0.005, cutoffSpeed * dy * 0.01, cutoffSpeed * dz * 0.005)
            } else {
                if (!thisMob.onGround) {
                    thisMob.addVelocity(0.0, -0.008, 0.0)
                }
                super.tick()
            }
        }
    }

    inner class WanderAroundOnSurfaceGoal(private val speed: Double): Goal()
    {
        private val world = thisMob.world
        private var x: Double = 0.0
        private var y: Double = 0.0
        private var z: Double = 0.0

        init { controls = EnumSet.of(Control.MOVE) }

        override fun canStart(): Boolean {
            if (!world.isDay || thisMob.isTouchingWater) return false

            val (x, y, z) = computeWanderTarget() ?: return false
            this.x = x
            this.y = y
            this.z = z
            return true
        }

        override fun shouldContinue() = !thisMob.navigation.isIdle

        override fun start() { thisMob.navigation.startMovingTo(x, y, z, speed) }

        private fun computeWanderTarget(): Vec3d? {
            val random = thisMob.random
            val pos = thisMob.blockPos
            val curPos = pos.mutableCopy()

            for (i in 0..10) {
                curPos.add(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10)
                if (world.getBlockState(curPos).isOf(Blocks.WATER))
                    return Vec3d.ofBottomCenter(curPos)
                curPos.set(pos) // reset to origin and try again
            }
            return null // can't find any
        }
    }

    inner class LeaveWaterGoal(
        speed: Double,
        range: Int = 8,
        maxYDifference: Int = 2
    ) : MoveToTargetPosGoal(this, speed, range, maxYDifference)
    {
        override fun canStart()
            = super.canStart() &&
                    !thisMob.world.isDay &&
                    !thisMob.isTouchingWater &&
                    thisMob.y >= thisMob.world.seaLevel - 3

        override fun isTargetPos(worldView: WorldView, blockPos: BlockPos): Boolean {
            val up = blockPos.up()
            // check if we have two blocks of headroom and somewhere to land on
            return world.isAir(up) &&
                    world.isAir(up.up()) &&
                    world.getBlockState(blockPos).hasSolidTopSurface(world, blockPos, thisMob)
        }

        override fun start() {
            thisMob.isTargetingUnderwater = true
            thisMob.navigation = thisMob.landNavigation
            super.start()
        }
    }

    inner class TargetAboveWaterGoal(
        private val speed: Double,
        private val minY: Int,
        private val maxHorizontalDistance: Int = 4,
        private val maxVerticalDistance: Int = 8,
        private val maxAngleDifference: Double = PI / 2, // or 90 degrees
    ): Goal()
    {
        private var foundTarget = false

        override fun canStart()
            = !thisMob.world.isDay && thisMob.isTouchingWater && thisMob.y < minY - 2

        override fun shouldContinue() = canStart() && !foundTarget

        override fun tick() {
            if (thisMob.y < minY - 1 && (thisMob.navigation.isIdle || thisMob.hasFinishedCurrentPath)) {
                val posToMoveTo = TargetFinder.findTargetTowards(
                    thisMob,
                    maxHorizontalDistance,
                    maxVerticalDistance,
                    Vec3d(thisMob.x, (minY - 1).toDouble(), thisMob.z),
                    maxAngleDifference
                )

                if (posToMoveTo == null) {
                    // no need to move anywhere since target is already in sight
                    foundTarget = true
                } else {
                    // move to the target's position
                    thisMob.navigation.startMovingTo(pos.x, pos.y, pos.z, speed)
                }
            }
        }

        override fun start() {
            thisMob.isTargetingUnderwater = true
            foundTarget = false
        }

        override fun stop() {
            thisMob.isTargetingUnderwater = false
        }
    }
}


