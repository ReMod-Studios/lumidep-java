package com.remodstudios.lumidep.data.fabric

import com.remodstudios.lumidep.data.DoseCounterComponent
import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag

class DoseCounterComponentImpl: DoseCounterComponent(), Component, CommonTickingComponent, AutoSyncedComponent {
    override fun readFromNbt(tag: CompoundTag) {
        ticksSinceIntoxicated = tag.getLong("ticksSinceIntoxicated")
        currentDose = tag.getDouble("currentDose")
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.putLong("ticksSinceIntoxicated", ticksSinceIntoxicated)
        tag.putDouble("currentDose", currentDose)
    }

    override fun tick() {
        tickLogic()
    }
}

@Suppress("unused")
object DoseCounterComponentKtImpl {
    @JvmStatic
    fun _get(user: PlayerEntity): DoseCounterComponent = Components.DOSE_COUNTER[user]
}
