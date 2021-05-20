package com.remodstudios.lumidep.data.fabric

import com.remodstudios.lumidep.data.DoseCounterComponent
import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag

class DoseCounterComponentImpl: DoseCounterComponent, Component, CommonTickingComponent, AutoSyncedComponent {
    override var currentDose = 0.0
    override var ticksSinceIntoxicated = 0L

    override fun readFromNbt(tag: CompoundTag) {
        currentDose = tag.getDouble("currentDose")
        ticksSinceIntoxicated = tag.getLong("ticksSinceIntoxicated ")
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.putDouble("currentDose", currentDose)
        tag.putLong("ticksSinceIntoxicated", ticksSinceIntoxicated)
    }

    override fun tick() {
        super.tick()
    }
}

@Suppress("unused")
object DoseCounterComponentKtImpl {
    @JvmStatic
    fun _get(user: PlayerEntity): DoseCounterComponent = Components.DOSE_COUNTER[user]
}
