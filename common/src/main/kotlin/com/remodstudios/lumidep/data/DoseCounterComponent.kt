package com.remodstudios.lumidep.data

import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.entity.player.PlayerEntity
import kotlin.math.exp

abstract class DoseCounterComponent {
    var currentDose = 0.0
        set(value) {
            ticksSinceIntoxicated = 0L // reset
            intoxicated = value > 0.0
            field = if (value <= 1E-2) {
                0.0
            } else {
                value
            }
        }
    var ticksSinceIntoxicated = 0L
    private var intoxicated = false

    fun tickLogic() {
        if (intoxicated) {
            currentDose *= exp(-ticksSinceIntoxicated.toDouble() / 80.0)
            ticksSinceIntoxicated++
        }
    }

    companion object {
        fun get(user: PlayerEntity): DoseCounterComponent = _get(user)
    }
}
@ExpectPlatform
fun _get(user: PlayerEntity): DoseCounterComponent { throw AssertionError() }