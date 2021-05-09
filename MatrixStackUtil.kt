package com.remodstudios.remodcore

import net.minecraft.client.util.math.MatrixStack

fun MatrixStack.frame(block: () -> Unit) {
    this.push()
    block()
    this.pop()
}