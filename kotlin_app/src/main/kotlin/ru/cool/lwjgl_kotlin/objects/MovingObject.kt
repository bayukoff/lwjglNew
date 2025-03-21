package ru.cool.lwjgl_kotlin.objects

import org.joml.Vector3f

interface MovingObject {
    fun move(vec: Vector3f)
    fun move(x: Float, y: Float, z: Float)
    fun updateMatrix()
}