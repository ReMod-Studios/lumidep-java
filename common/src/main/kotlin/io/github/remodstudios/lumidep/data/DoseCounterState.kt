package io.github.remodstudios.lumidep.data

import net.minecraft.nbt.CompoundTag
import net.minecraft.util.math.MathHelper
import ru.nsk.kstatemachine.*

class DoseCounterState {
    var ticks = 0L
        private set
    var targetDose = 0.0
        private set
    var prevDose = 0.0
        private set

    // TODO: cache & use different interpolation functions
    val currentDose get() = MathHelper.lerp(ticks / duration.toDouble(), prevDose, targetDose)

    var duration = 0L
        private set

    override fun toString()
        = "DoseCounter|t=$ticks/$duration|dose: $prevDose +--- $currentDose ---> $targetDose|"

    fun tick() {
        // if we are currently idle, do nothing
        stateMachine.processEvent(Events.Tick)
    }

    fun consume(amount: Double) {
        stateMachine.processEvent(Events.AddDose(amount))
    }

    fun readFromNbt(tag: CompoundTag) {
        ticks = tag.getLong("ticks")
        targetDose = tag.getDouble("targetDose")
        prevDose = tag.getDouble("prevDose")
    }

    fun writeToNbt(tag: CompoundTag) {
        tag.putLong("ticks", ticks)
        tag.putDouble("targetDose", targetDose)
        tag.putDouble("prevDose", prevDose)
    }

    private fun setNewTargetDose(target: Double) {
        prevDose = currentDose
        targetDose = target
    }

    private fun freezeAt(target: Double) {
        prevDose = target
        targetDose = target
    }

    private val stateMachine = createStateMachine {
        addInitialState(States.Down) {
            //onEntry { freezeAt(0.0) }
            dataTransition<Events.AddDose, Double> {
                targetState = States.Rush
            }
        }
        addState(States.Rush) {
            transition<Events.Tick> {
                targetState = States.High
            }
        }
        addState(States.High) {
            transition<Events.Tick> {
                targetState = States.Fall
            }
        }
        addState(States.Fall) {
            transition<Events.Tick> {
                targetState = States.Down
            }
        }
        /*
        addState(States.Rush) {
            onEntry {
                duration = -1
                setNewTargetDose(currentDose + data)
            }
            //transitionConditionally<Events.Tick> {
            //    direction = stayFor(duration, targetState(States.High))
            //}
        }

         */
        /*
        addState(States.High) {
            onEntry { freezeAt(currentDose) }
            transitionConditionally<Events.Tick> {
                direction = stayFor(duration, targetState(States.Fall))
            }
        }
        addState(States.Fall) {
            onEntry { setNewTargetDose(0.0) }
            transitionConditionally<Events.Tick> {
                direction = stayFor(duration, targetState(States.Down))
            }
        }

         */
    }

    private inline fun <reified E: Event> stayFor(
        duration: Long,
        next: TransitionDirection
    ) : (E) -> TransitionDirection =
        {
            if (ticks >= duration) { next }
            else {
                ticks++
                stay()
            }
        }

    object Events {
        class AddDose(override val data: Double): DataEvent<Double>
        object Tick: Event
    }

    object States {
        object Down: DefaultState("down")
        object Rush: DefaultDataState<Double>("rush")
        object High: DefaultState("high")
        object Fall: DefaultState("fall")
    }
}