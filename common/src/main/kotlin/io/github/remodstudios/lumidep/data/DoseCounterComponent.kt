package io.github.remodstudios.lumidep.data

import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import kotlin.math.exp
import kotlin.math.pow

abstract class DoseCounterComponent(val provider: Any) {
    //protected val state = DoseCounterState()
    //val currentDose by state::currentDose

    var state: DoseState = DoseState.Low
        private set

    var ticksInState: Long = 0L
        private set

    var currentDose: Double = 0.0
        private set

    fun consume(dose: Double) {
        currentDose += dose
        state = DoseState.Rush
        println(state)
    }

    // sorry i'm really bad at naming
    val dramaticness get() = (currentDose / LETHAL_DOSE).pow(5.0/8.0)

    fun tick() {
        println("before\nstate: $state\nt=$ticksInState, cur=$currentDose\n")

        if (state.nextState != state) {
            currentDose += state.differentiate(ticksInState)
            if (state.readyToProgress(ticksInState)) {
                state = state.nextState
                ticksInState = 0
            } else {
                ticksInState++
            }
        }

        println("after\nstate: $state\nt=$ticksInState, cur=$currentDose\n")
        sync()
    }

    abstract fun sync()

    open fun readFromNbt(tag: CompoundTag) {
        //state.readFromNbt(tag)
    }

    open fun writeToNbt(tag: CompoundTag) {
        //state.writeToNbt(tag)
    }

    companion object {
        //TODO: make these configurable
        const val LETHAL_DOSE = 30.0

        @JvmStatic
        fun get(user: PlayerEntity): DoseCounterComponent = _get(user)
    }




}
sealed class DoseState {
    abstract val nextState: DoseState
    abstract fun readyToProgress(t: Long): Boolean
    abstract fun differentiate(t: Long): Double

    object Low : DoseState() {
        override val nextState = Low // this should never trigger, though
        override fun readyToProgress(t: Long) = false
        override fun differentiate(t: Long) = 0.0 // constant
    }

    sealed class Ticking: DoseState() {
        abstract val duration: Long
        override fun readyToProgress(t: Long) = t >= duration
    }

    object Rush: DoseState.Ticking() {
        override val duration = 30L
        override val nextState = High
        override fun differentiate(t: Long): Double = 3 * t.toDouble().pow(2)// d(x^3)/dx = 3x^2
    }

    object High: DoseState.Ticking() {
        override val duration = 280L
        override val nextState = Fall
        override fun differentiate(t: Long) = 0.0 // constant
    }

    object Fall: DoseState.Ticking() {
        override val duration = 50L
        override val nextState = Low
        // should be the derivative of -e^(2x-3)+C if I've done my mafs:tm: correctly
        override fun differentiate(t: Long) = -2 * exp(2 * t.toDouble() - 3) / 10
    }
}

@ExpectPlatform
private fun _get(user: PlayerEntity): DoseCounterComponent { throw AssertionError() }