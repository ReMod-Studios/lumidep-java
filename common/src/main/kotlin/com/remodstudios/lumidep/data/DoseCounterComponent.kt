package com.remodstudios.lumidep.data

import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.entity.player.PlayerEntity
import kotlin.math.exp

interface DoseCounterComponent {
    var currentDose: Double
    var ticksSinceIntoxicated: Long

    fun tick() {
        if (currentDose < 0) {
            currentDose = 0.0 // reset
            ticksSinceIntoxicated = 0L
            return
        } else if (currentDose == 0.0) {
            return
        }
        currentDose *= exp(-ticksSinceIntoxicated.toDouble())
        ticksSinceIntoxicated++
    }

    companion object {
        fun get(user: PlayerEntity): DoseCounterComponent = _get(user)
    }
}
@ExpectPlatform
fun _get(user: PlayerEntity): DoseCounterComponent { throw AssertionError() }