package com.remodstudios.lumidep.worldgen

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.block.LumidepBlocks
import com.remodstudios.remodcore.BiomeRegistryHelper
import com.remodstudios.remodcore.effects
import net.minecraft.block.Blocks
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.biome.SpawnSettings
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig
import net.minecraft.world.gen.feature.DefaultBiomeFeatures as Dbf

object LumidepBiomes: BiomeRegistryHelper(Lumidep.MOD_ID) {
    private val OBSIDIAN_SURFACE_BUILDER: ConfiguredSurfaceBuilder<TernarySurfaceConfig> = SurfaceBuilder.DEFAULT
        .withConfig(
            TernarySurfaceConfig(
                Blocks.GRASS_BLOCK.defaultState,
                Blocks.DIRT.defaultState,
                LumidepBlocks.BLACK_SAND.defaultState,
            )
        )

    val SOMEDING = add("someding") { spawn, gen ->
        Dbf.addFarmAnimals(spawn)
        Dbf.addMonsters(spawn, 95, 5, 100)

        gen.surfaceBuilder(OBSIDIAN_SURFACE_BUILDER)

        precipitation(Biome.Precipitation.RAIN)
        category(Biome.Category.NONE)
        depth(0.125F)
        scale(0.05F)
        temperature(0.8F)
        downfall(0.4F)
        effects {
            waterColor(0x3f76e4)
            waterFogColor(0x050533)
            fogColor(0xc0d8ff)
            skyColor(0x77adff)
        }
    }
}