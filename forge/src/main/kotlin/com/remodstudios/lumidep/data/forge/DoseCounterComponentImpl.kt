package com.remodstudios.lumidep.data.forge

import com.remodstudios.lumidep.data.DoseCounterComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.util.math.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional

class DoseCounterComponentImpl: DoseCounterComponent {
    override var currentDose = 0.0
    override var ticksSinceIntoxicated = 0L
}

class DoseCounterStorage: Capability.IStorage<DoseCounterComponent> {
    override fun writeNBT(
        capability: Capability<DoseCounterComponent>,
        component: DoseCounterComponent,
        direction: Direction
    ): Tag = CompoundTag().apply {
        putDouble("currentDose", component.currentDose)
        putLong("ticksSinceIntoxicated", component.ticksSinceIntoxicated)
    }

    override fun readNBT(
        capability: Capability<DoseCounterComponent>,
        component: DoseCounterComponent,
        direction: Direction,
        tag: Tag
    ) {
        if (tag !is CompoundTag) throw IllegalStateException("expected a compound tag")

        component.currentDose = tag.getDouble("currentDose")
        component.ticksSinceIntoxicated = tag.getLong("ticksSinceIntoxicated")
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