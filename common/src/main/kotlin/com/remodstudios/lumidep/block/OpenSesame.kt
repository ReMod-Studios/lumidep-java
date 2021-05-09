package com.remodstudios.lumidep.block

import net.minecraft.block.*

open class OpenPressurePlateBlock(activationRule: ActivationRule, settings: Settings)
    : PressurePlateBlock(activationRule, settings)

open class OpenWoodenButtonBlock(settings: Settings)
    : WoodenButtonBlock(settings)

open class OpenDoorBlock(settings: Settings)
    : DoorBlock(settings)

open class OpenTrapdoorBlock(settings: Settings)
    : TrapdoorBlock(settings)

open class OpenStairsBlock(blockState: BlockState, settings: Settings)
    : StairsBlock(blockState, settings)