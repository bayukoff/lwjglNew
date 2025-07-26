package ru.cool.lwjgl_kotlin.animation

import org.joml.Quaternionf

data class QuaternionKey(val value: Quaternionf, override val time: Double): AnimKey(time)