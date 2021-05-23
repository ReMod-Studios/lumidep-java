package com.remodstudios.lumidep.data

import com.remodstudios.remodcore.syncedDelegate
import com.tinder.StateMachine
import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.entity.player.PlayerEntity
import kotlin.math.exp
import kotlin.math.pow


abstract class DoseCounterComponent(val provider: Any) {
    var currentDose by syncedDelegate(0.0, ::sync)
        private set
    var prevDose by syncedDelegate(0.0, ::sync)
        private set
    var targetDose by syncedDelegate(0.0, ::sync)
        private set
    var ticksPassed by syncedDelegate(0L, ::sync)
    private var state: State by syncedDelegate(State.Low, ::sync)

    fun incrementDose(dose: Double) {
        prevDose = currentDose
        targetDose = dose
        state = State.Surge
    }

    // sorry i'm really bad at naming
    val dramaticness get() = (currentDose / LETHAL_DOSE).pow(5.0/8.0)

    fun tickLogic() {
        if (!intoxicated) return

        if (ticksSinceIntoxicated > CUTOFF_POINT)
            currentDose *= exp(-(ticksSinceIntoxicated.toDouble() - CUTOFF_POINT) / 320.0)
        ticksSinceIntoxicated++
        when (ticksSinceIntoxicated) {
            in Long.MIN_VALUE..0 -> {}
            in 1E-2..20 -> {}
        }
        sync()
    }

    abstract fun sync()

    companion object {
        //TODO: make these configurable
        val LETHAL_DOSE = 30.0

        @JvmStatic
        fun get(user: PlayerEntity): DoseCounterComponent = _get(user)
    }

    val stateMachine = StateMachine.create<State, Event, SideEffect> {
        initialState(State.Low)
        state<State.Low> {
            on<Event.OnConsumption> {
                transitionTo(State.Surge, SideEffect.UpdateTargetDose(it.amount))
            }
        }
        state<State.Surge> {
            on<Event.Stabilize> {
                transitionTo(State.High, SideEffect.MaintainDose)
            }
        }
        state<State.High> {
            on<Event.Decay> {
                transitionTo(State.Fall, SideEffect.UpdateTargetDose(0.0))
            }
        }
        state<State.Fall> {
            on<Event.End> {
                transitionTo(State.Low)
            }
        }

        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
            val counter = this@DoseCounterComponent
            val sideEffect = validTransition.sideEffect
            when (sideEffect) {
                SideEffect.MaintainDose -> counter.targetDose = counter.prevDose
                is SideEffect.UpdateTargetDose -> {
                    counter.prevDose = counter.targetDose
                    counter.targetDose = sideEffect.to
                }
            }
        }
    }

    sealed class Event {
        class OnConsumption(val amount: Double): Event()
        object Stabilize: Event()
        object Decay: Event()
        object End: Event()
    }
    sealed class SideEffect {
        object MaintainDose: SideEffect()
        class UpdateTargetDose(val to: Double): SideEffect()
    }

    sealed class State {
        object Low: State()

        abstract class Timed(val duration: Long): State()
        object Surge: State.Timed(30)
        object High: State.Timed(280)
        object Fall: State.Timed(40)
    }
}

@ExpectPlatform
private fun _get(user: PlayerEntity): DoseCounterComponent { throw AssertionError() }