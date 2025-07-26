package ru.cool.lwjgl_kotlin.utils

import ru.cool.lwjgl_kotlin.glObjects.VAO

object VaoHelper {
    fun setVertexAttributes(vao: VAO, includeNormals: Boolean, includeUV: Boolean, includeBones: Boolean) {
        var offset = 0
        var index = 0
        vao.setVertexAttribute(index++, 3, stride(includeNormals, includeUV, includeBones), offset)
        offset += 3 * Float.SIZE_BYTES
        if (includeNormals) {
            vao.setVertexAttribute(index++, 3, stride(true, includeUV, includeBones), offset)
            offset += 3 * Float.SIZE_BYTES
        }
        if (includeUV) {
            vao.setVertexAttribute(index++, 2, stride(includeNormals, true, includeBones), offset)
            offset += 2 * Float.SIZE_BYTES
        }
        if (includeBones) {
            vao.setVertexAttribute(index++, 4, stride(includeNormals, includeUV, true), offset)
            offset += 4 * Float.SIZE_BYTES
            vao.setVertexAttribute(index++, 4, stride(includeNormals, includeUV, true), offset)
            offset += 4 * Float.SIZE_BYTES
        }
    }

    private fun stride(includeNormals: Boolean, includeUV: Boolean, includeBones: Boolean): Int {
        var components = 3
        if (includeNormals) components += 3
        if (includeUV) components += 2
        if (includeBones) components += 8
        return components * Float.SIZE_BYTES
    }

}