package com.remodstudios.lumidep.forge

import com.remodstudios.lumidep.item.LumidepItems
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

@Suppress("unused")
object LumidepPlatformInterfaceImpl {
    @JvmStatic fun getItemGroup(): ItemGroup {
        return object : ItemGroup("lumidep.group") {
            override fun createIcon(): ItemStack {
                return ItemStack(LumidepItems.ESCA.get())
            }
        }
    }
}