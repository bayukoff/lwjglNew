package ru.cool.lwjgl_kotlin.geometry

import java.nio.FloatBuffer
import java.nio.IntBuffer

abstract class Geometry(
    open var vertices: FloatBuffer,
    open var indices: IntBuffer
    ) {

}