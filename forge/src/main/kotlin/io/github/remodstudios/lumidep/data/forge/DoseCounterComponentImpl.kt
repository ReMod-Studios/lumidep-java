package io.github.remodstudios.lumidep.data.forge

import io.github.remodstudios.lumidep.data.DoseCounterComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.util.math.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional

// TODO
class DoseCounterComponentImpl : DoseCounterComponent(0) {
    override fun sync() {
        // TODO: make packets and shit
    }
}

class DoseCounterStorage: Capability.IStorage<DoseCounterComponent> {
    override fun writeNBT(
        capability: Capability<DoseCounterComponent>,
        component: DoseCounterComponent,
        direction: Direction
    ): Tag = CompoundTag().also { component.writeToNbt(it) }

    override fun readNBT(
        capability: Capability<DoseCounterComponent>,
        component: DoseCounterComponent,
        direction: Direction,
        tag: Tag
    ) {
        if (tag !is CompoundTag) throw IllegalStateException("expected a compound tag")

        component.readFromNbt(tag)
    }
}

class DoseCounterProvider: ICapabilityProvider {
    companion object {
        @JvmStatic
        @CapabilityInject(DoseCounterComponent::class)
        lateinit var DOSE_COUNTER: Capability<DoseCounterComponent>
    }
    // if everything goes correctly, this should never throw
    private val instance = DOSE_COUNTER.defaultInstance

    @Suppress("unchecked_cast")
    override fun <T : Any> getCapability(capability: Capability<T>, arg: Direction?): LazyOptional<T>
        = when (capability) {
            DOSE_COUNTER -> LazyOptional.of { instance as T }
            else         -> LazyOptional.empty()
        }
}

@Suppress("unused")
object DoseCounterComponentKtImpl {
    @JvmStatic
    fun _get(user: PlayerEntity): DoseCounterComponent
        = user.getCapability(DoseCounterProvider.DOSE_COUNTER)
            .orElseThrow { IllegalStateException("player does not have given capability") }
}