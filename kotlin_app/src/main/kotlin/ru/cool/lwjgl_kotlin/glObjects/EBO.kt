package ru.cool.lwjgl_kotlin.glObjects

import org.lwjgl.opengl.GL33
import java.nio.*

class EBO {
    val eboID = GL33.glGenBuffers()

    fun bindBuffer() = GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, eboID)
    fun unbindBuffer() = GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, 0)

    fun putDataToBuffer(buffer: IntBuffer) {
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW)
    }
}