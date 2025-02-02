package ru.cool.lwjgl_kotlin.glObjects

import org.lwjgl.opengl.GL30
import java.nio.*

class VBO {
    val vboID = GL30.glGenBuffers()

    fun bindBuffer() = GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
    fun unbindBuffer() = GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)

    fun <B: Buffer> putDataToBuffer(buffer: B) {
        if (buffer is FloatBuffer)
            GL30.glBufferData(
                GL30.GL_ARRAY_BUFFER,
                buffer as FloatBuffer,
                GL30.GL_STATIC_DRAW)
        else if (buffer is IntBuffer)
            GL30.glBufferData(
                GL30.GL_ARRAY_BUFFER,
                buffer as IntBuffer,
                GL30.GL_STATIC_DRAW)
        else if (buffer is ByteBuffer)
            GL30.glBufferData(
                GL30.GL_ARRAY_BUFFER,
                buffer as ByteBuffer,
                GL30.GL_STATIC_DRAW)
        else if (buffer is LongBuffer)
            GL30.glBufferData(
                GL30.GL_ARRAY_BUFFER,
                buffer as LongBuffer,
                GL30.GL_STATIC_DRAW)
        else if (buffer is DoubleBuffer)
            GL30.glBufferData(
                GL30.GL_ARRAY_BUFFER,
                buffer as DoubleBuffer,
                GL30.GL_STATIC_DRAW)
        else throw IllegalArgumentException("Unsupported buffer")
    }
}