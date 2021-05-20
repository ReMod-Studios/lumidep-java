package com.remodstudios.lumidep

import com.remodstudios.lumidep.block.LumidepBlocks
import com.remodstudios.lumidep.block.entity.LumidepBlockEntities
import com.remodstudios.lumidep.entity.LumidepEntities
import com.remodstudios.lumidep.item.LumidepItems
import com.remodstudios.lumidep.worldgen.LumidepBiomes
import me.shedaniel.architectury.registry.BiomeModifications
import me.shedaniel.architectury.registry.fuel.FuelRegistry
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier

object Lumidep {
    const val MOD_ID = "lumidep"

    fun init() {
        LumidepEntities.register()
        LumidepItems.register()
        LumidepBlocks.register()
        LumidepBlockEntities.register()
        //LumidepBiomes.register()


        // TODO: make this configurable?
        val coalBurnTime = FuelRegistry.get(ItemStack(Items.COAL))
        FuelRegistry.register((coalBurnTime * 1.2f).toInt(), LumidepItems.TUNGSTEN_CARBON)
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}