package ru.cool.lwjgl_kotlin.glObjects

import org.lwjgl.opengl.GL30
import java.nio.*

class EBO {
    val eboID = GL30.glGenBuffers()

    fun bindBuffer() = GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, eboID)
    fun unbindBuffer() = GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0)

    fun putDataToBuffer(buffer: IntBuffer) {
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW)
    }
}