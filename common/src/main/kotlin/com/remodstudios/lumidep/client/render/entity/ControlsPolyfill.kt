package com.remodstudios.lumidep.client.render.entity

import com.remodstudios.remodcore.toDeg
import com.remodstudios.remodcore.toRad
import net.minecraft.entity.ai.control.LookControl
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.MobEntity
import net.minecraft.util.math.MathHelper as MH

// Polyfills for missing features from 1.17, as in 21w18a, Yarn build 10
// These should match with Vanilla in terms of functionality, but there's a number of parts that got rewritten to fit my
// personal style. Therefore, a 100% compatibility with Vanilla is not guaranteed.

// original: MoveControl#wrapDegrees
fun lerpAndWrapDegrees(from: Float, to: Float, max: Float): Float {
    var wrappedDiff = MH.wrapDegrees(to - from)

    // clamp
    if (wrappedDiff > max) wrappedDiff = max
    if (wrappedDiff < -max) wrappedDiff = -max

    var lerped = from + wrappedDiff
    // clamp again
    if (lerped < 0f) lerped += 360f
    else if (lerped > 360f) lerped -= 360f

    return lerped
}

class AquaticMoveControl(
    mobEntity: MobEntity,
    private val pitchChange: Int, // could be a float, dunno what mojang's thinking
    private val yawChange: Int,   // ditto
    private val speedInWater: Float,
    private val speedInAir: Float,
    private val buoyant: Boolean
) : MoveControl(mobEntity)
{
    override fun tick() {
        if (this.buoyant && entity.isTouchingWater)
            // added velocity to stay afloat
            this.entity.addVelocity(0.0, 0.005, 0.0)

        if (state != State.MOVE_TO || entity.navigation.isIdle) {
            // stay still
            entity.movementSpeed = 0f
            entity.sidewaysSpeed = 0f
            entity.upwardSpeed = 0f
            entity.forwardSpeed = 0f
            return
        }

        val dx = targetX - entity.x
        val dy = targetY - entity.y
        val dz = targetZ - entity.z

        // if we are near enough to the target, stop moving
        val magSquared = dx*dx + dy*dy + dz*dz
        if (magSquared < 2.5) {
            entity.forwardSpeed = 0f
            return
        }

        // move to the direction of the target
        setYaw(dx, dz)

        val speedMultiplier = (speed * entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)).toFloat()
        if (entity.isTouchingWater) {
            entity.movementSpeed = speedMultiplier * speedInWater
            setPitch(dx, dy, dz)  // only set pitch if we're in water

            val xComponent = MH.cos(entity.pitch.toRad())
            val yComponent = MH.sin(entity.pitch.toRad())

            // move based on pitch
            entity.forwardSpeed = xComponent * speedMultiplier
            entity.upwardSpeed = -yComponent * speedMultiplier
        } else {
            entity.movementSpeed = speedMultiplier * speedInAir
        }
    }

    private fun setYaw(dx: Double, dz: Double) {
        val newYaw = MH.atan2(dz, dx).toDeg() - 90f
        val lerpedYaw = lerpAndWrapDegrees(entity.yaw, newYaw.toFloat(), yawChange.toFloat())
        entity.yaw = lerpedYaw
        entity.bodyYaw = lerpedYaw
        entity.headYaw = lerpedYaw
    }

    private fun setPitch(dx: Double, dy: Double, dz: Double) {
        // calculates the angle between the y-distance and the distance in the xz-plane...?
        val newPitch = -MH.atan2(dy, MH.sqrt(dx * dx + dz * dz).toDouble()).toDeg().toFloat()

        val lerpedPitch = MH.clamp(
            MH.wrapDegrees(newPitch),
            -pitchChange.toFloat(),
            pitchChange.toFloat(),
        )
        entity.pitch = lerpAndWrapDegrees(entity.pitch, lerpedPitch, 5f)
    }

}

class AquaticLookControl(
    mobEntity: MobEntity,
    private val maxYawDifference: Int, // could be a float
    private val addedPitch: Int = 20,
    private val addedYaw: Int = 10,
) : LookControl(mobEntity)
{
    override fun tick() {
        if (active) {
            active = false
            entity.headYaw = changeAngle(entity.headYaw, targetYaw + addedYaw, yawSpeed)
            entity.pitch = changeAngle(entity.pitch, targetPitch + addedPitch, pitchSpeed)
        } else {
            if (entity.navigation.isIdle)
                // slowly return to natural pitch
                entity.pitch = changeAngle(entity.pitch, 0f, 5f)
            entity.headYaw = changeAngle(entity.headYaw, entity.bodyYaw, yawSpeed)
        }

        // how much does the body still need to turn
        val yawDiff = MH.wrapDegrees(entity.headYaw - entity.bodyYaw)
        if (yawDiff < -maxYawDifference.toFloat()) {
            entity.bodyYaw -= 4f
        }
        else if (yawDiff > maxYawDifference.toFloat()) {
            entity.bodyYaw += 4f
        }
    }
}