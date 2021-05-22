package com.remodstudios.lumidep.data

import com.remodstudios.lumidep.Lumidep
import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.entity.player.PlayerEntity
import kotlin.math.exp
import kotlin.math.pow

abstract class DoseCounterComponent(val provider: Any) {
    var currentDose = 0.0
        set(value) {
            intoxicated = value > 0.0
            field = if (value <= 1E-2) {
                ticksSinceIntoxicated = 0L
                0.0
            } else {
                value
            }
            sync()
        }
    var ticksSinceIntoxicated = 0L
    private var intoxicated = false

    // sorry i'm really bad at naming
    val dramaticness get() = (currentDose / LETHAL_DOSE).pow(5.0/8.0)

    fun tickLogic() {
        if (intoxicated) {
            if (ticksSinceIntoxicated > CUTOFF_POINT)
                currentDose *= exp(-(ticksSinceIntoxicated.toDouble() - CUTOFF_POINT) / 320.0)
            ticksSinceIntoxicated++
            sync()
        }
    }

    abstract fun sync()

    companion object {
        //TODO: make this configurable
        val LETHAL_DOSE = 30.0
        const val CUTOFF_POINT = 300

        @JvmStatic
        fun get(user: PlayerEntity): DoseCounterComponent = _get(user)
    }
}
@ExpectPlatform
fun _get(user: PlayerEntity): DoseCounterComponent { throw AssertionError() }