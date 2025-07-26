package ru.cool.lwjgl_kotlin.animation

import org.joml.Vector3f

data class VectorKey(val value: Vector3f, override val time: Double): AnimKey(time) {

}