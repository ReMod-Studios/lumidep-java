package com.remodstudios.remodcore

import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Position
import net.minecraft.util.math.Vec3d
import kotlin.math.PI
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Miscellaneous extensions

// Destructuring support
operator fun Position.component1(): Double = x
operator fun Position.component2(): Double = y
operator fun Position.component3(): Double = z

// Vector stuff
fun Entity.squaredDistanceTo(blockPos: BlockPos)
        = squaredDistanceTo(blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble())

// MafsUtil:tm:
fun magnitude(x: Double, y: Double, z: Double)
        = MathHelper.sqrt(x*x + y*y + z*z)

fun Float.toDeg(): Float = this * 180f / PI.toFloat()
fun Float.toRad(): Float = this * PI.toFloat() / 180f
fun Double.toDeg(): Double = this * 180.0 / PI
fun Double.toRad(): Double = this * PI / 180.0

inline fun Boolean.ifTrueThenAlso(also: () -> Unit): Boolean {
    if (this) also()
    return this
}

inline fun MatrixStack.frame(block: () -> Unit) {
    this.push()
    block()
    this.pop()
}

operator fun Vec3d.unaryMinus(): Vec3d = this.multiply(-1.0) // can't use #negate since it's client-side only :mojank:

operator fun Vec3d.plus(by: Vec3d): Vec3d = this.add(by)
operator fun Vec3d.minus(by: Vec3d): Vec3d = this.subtract(by)
operator fun Vec3d.times(by: Double): Vec3d = this.multiply(by)
operator fun Vec3d.div(by: Double): Vec3d = this.multiply(1 / by)

fun PlayerEntity.applyStatusEffect(type: StatusEffect,
                                   duration: Int = 0,
                                   amplifier: Int = 0,
                                   ambient: Boolean = false,
                                   showParticles: Boolean = true,
                                   showIcon: Boolean = true,
                                   hiddenEffect: StatusEffectInstance? = null)
{
    applyStatusEffect(StatusEffectInstance(type, duration, amplifier, ambient, showParticles, showIcon, hiddenEffect))
}

fun argb(a: Int = 255, r: Int, g: Int, b: Int)
    = (a and 0xff shl 24) or (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)

inline fun <T> syncedDelegate(initialValue: T, crossinline onChange: () -> Unit)
    = Delegates.observable(initialValue) { _, _, _ -> onChange() }