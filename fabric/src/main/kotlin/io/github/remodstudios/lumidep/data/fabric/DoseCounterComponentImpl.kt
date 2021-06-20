package io.github.remodstudios.lumidep.data.fabric

import io.github.remodstudios.lumidep.data.DoseCounterComponent
import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag

class DoseCounterComponentImpl(provider: Any) : DoseCounterComponent(provider), Component, CommonTickingComponent, AutoSyncedComponent {
    override fun sync() {
        Components.DOSE_COUNTER.sync(provider)
    }
}

@Suppress("unused")
object DoseCounterComponentKtImpl {
    @JvmStatic
    fun _get(user: PlayerEntity): DoseCounterComponent = Components.DOSE_COUNTER[user]
}
