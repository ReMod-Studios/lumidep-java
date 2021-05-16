package com.remodstudios.lumidep.item

import com.remodstudios.lumidep.Lumidep
import com.remodstudios.lumidep.getItemGroup
import com.remodstudios.remodcore.ItemRegistryHelper
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.item.Item
import net.minecraft.item.SpawnEggItem
import net.minecraft.util.registry.Registry
import com.remodstudios.lumidep.block.LumidepBlocks as Lb
import com.remodstudios.lumidep.entity.LumidepEntities as Le

object LumidepItems: ItemRegistryHelper(Lumidep.MOD_ID) {
    val REGISTRY = DeferredRegister.create(Lumidep.MOD_ID, Registry.ITEM_KEY)
    val GROUP = getItemGroup();

    override fun defaultSettings(): Item.Settings = Item.Settings().group(GROUP)

    val ESCA = add("esca")
    val TUNGSTEN_INGOT = add("tungsten_ingot")
    val TUNGSTEN_NUGGET = add("tungsten_nugget")

    val TUNGSTEN_BLOCK = add("tungsten_block", Lb.TUNGSTEN_BLOCK);
    val TUNGSTEN_DEPOSIT = add("tungsten_deposit", Lb.TUNGSTEN_DEPOSIT);
    val BLACK_SAND = add("black_sand", Lb.BLACK_SAND);
    val CORALLINE_ALGAE = add("coralline_algae", Lb.CORALLINE_ALGAE);
    val DEAD_KELP = add("dead_kelp", Lb.DEAD_KELP);
    val LUMEROCK = add("lumerock", Lb.LUMEROCK);
    val BRACKWOOD_LOG = add("brackwood_log", Lb.BRACKWOOD_LOG);
    val STRIPPED_BRACKWOOD_LOG = add("stripped_brackwood_log", Lb.STRIPPED_BRACKWOOD_LOG);
    val BRACKWOOD_WOOD = add("brackwood_wood", Lb.BRACKWOOD_WOOD);
    val STRIPPED_BRACKWOOD_WOOD = add("stripped_brackwood_wood", Lb.STRIPPED_BRACKWOOD_WOOD);
    val BRACKWOOD_PLANKS = add("brackwood_planks", Lb.BRACKWOOD_PLANKS);
    val BRACKWOOD_PRESSURE_PLATE = add("brackwood_pressure_plate", Lb.BRACKWOOD_PRESSURE_PLATE);
    val BRACKWOOD_BUTTON = add("brackwood_button", Lb.BRACKWOOD_BUTTON);
    val BRACKWOOD_DOOR = add("brackwood_door", Lb.BRACKWOOD_DOOR);
    val BRACKWOOD_TRAPDOOR = add("brackwood_trapdoor", Lb.BRACKWOOD_TRAPDOOR);
    val BRACKWOOD_SIGN = addWithFactory("brackwood_sign") {
        BrackwoodSignItem(this, Lb.BRACKWOOD_SIGN, Lb.BRACKWOOD_WALL_SIGN)
    };
    val BRACKWOOD_SLAB = add("brackwood_slab", Lb.BRACKWOOD_SLAB);
    val BRACKWOOD_STAIRS = add("brackwood_stairs", Lb.BRACKWOOD_STAIRS);
    val BRACKWOOD_FENCE = add("brackwood_fence", Lb.BRACKWOOD_FENCE);
    val BRACKWOOD_FENCE_GATE = add("brackwood_fence_gate", Lb.BRACKWOOD_FENCE_GATE);

    val ADULT_KREPLER_SPAWN_EGG = addWithFactory("adult_krepler_spawn_egg") { SpawnEggItem(Le.ADULT_KREPLER, 0x5D703B, 0x963B12, this) }
    val ANGLERFISH_SPAWN_EGG = addWithFactory("anglerfish_spawn_egg") { SpawnEggItem(Le.ANGLERFISH, 0x29292D, 0xA7B1FB, this) }
  /* TODO entities
    val BROKEN_GUARDIAN_SPAWN_EGG = add("broken_guardian_spawn_egg") { SpawnEggItem(Le.BROKEN_GUARDIAN, 0x5D6362, 0x744332, this) };
    val GOBLIN_SHARK_SPAWN_EGG = add("goblin_shark_spawn_egg") { SpawnEggItem(Le.GOBLIN_SHARK, 0x93688C, 0xA57B97, this) };
    val GORGE_BEAST_SPAWN_EGG = add("gorge_beast_spawn_egg") { SpawnEggItem(Le.GORGE_BEAST, 0x1A282D, 0x00FF93, this) };
    val ISOPOD_SPAWN_EGG = add("isopod_spawn_egg") { SpawnEggItem(Le.ISOPOD, 0x9D555D, 0xB68579, this) };
    val MANTARAY_SPAWN_EGG = add("mantaray_spawn_egg") { SpawnEggItem(Le.MANTARAY, 0x110F17, 0x888893, this) };
     */
}

