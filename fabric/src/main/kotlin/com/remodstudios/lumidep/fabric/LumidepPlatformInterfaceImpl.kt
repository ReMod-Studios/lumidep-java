package com.remodstudios.lumidep.fabric

import com.remodstudios.lumidep.Lumidep.id
import com.remodstudios.lumidep.LumidepItems
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

@Suppress("unused")
object LumidepPlatformInterfaceImpl {
    @JvmStatic fun getItemGroup(): ItemGroup {
        return FabricItemGroupBuilder.build(
            id("group")
        ) { ItemStack(LumidepItems.ESCA.get()) }
    }
}