package ru.cool.lwjgl_kotlin.loaders

import java.util.Arrays

class Face(var vertices: IntArray, var normals: IntArray, var uv: IntArray) {
    override fun toString(): String {
        return "Face (${vertices.contentToString()}, " +
                "${normals.contentToString()}, ${uv.contentToString()})"
    }

    fun isThereTexture(): Boolean = uv.any { it != 0 }
    fun isThereNormal(): Boolean = uv.any { it != 0 }

}