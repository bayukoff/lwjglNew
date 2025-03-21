package ru.cool.lwjgl_kotlin.geometry

import java.nio.FloatBuffer
import java.nio.IntBuffer

class CustomGeometry(override var vertices: FloatBuffer, override var indices: IntBuffer, override var hasUV: Boolean, override var hasNormal: Boolean): Geometry(vertices, indices,hasUV,hasNormal) {

}