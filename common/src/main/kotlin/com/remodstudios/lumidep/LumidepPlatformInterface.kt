@file:JvmName("LumidepPlatformInterface")
package com.remodstudios.lumidep

import me.shedaniel.architectury.annotations.ExpectPlatform
import net.minecraft.item.ItemGroup


@ExpectPlatform
fun getItemGroup(): ItemGroup = throw AssertionError()