package ru.cool.lwjgl_kotlin.glObjects

import org.lwjgl.opengl.GL33
import java.nio.*

class VBO {
    val vboID = GL33.glGenBuffers()

    fun bindBuffer() = GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboID)
    fun unbindBuffer() = GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0)

    fun <B: Buffer> putDataToBuffer(buffer: B) {
        if (buffer is FloatBuffer)
            GL33.glBufferData(
                GL33.GL_ARRAY_BUFFER,
                buffer as FloatBuffer,
                GL33.GL_STATIC_DRAW)
        else if (buffer is IntBuffer)
            GL33.glBufferData(
                GL33.GL_ARRAY_BUFFER,
                buffer as IntBuffer,
                GL33.GL_STATIC_DRAW)
        else if (buffer is ByteBuffer)
            GL33.glBufferData(
                GL33.GL_ARRAY_BUFFER,
                buffer as ByteBuffer,
                GL33.GL_STATIC_DRAW)
        else if (buffer is LongBuffer)
            GL33.glBufferData(
                GL33.GL_ARRAY_BUFFER,
                buffer as LongBuffer,
                GL33.GL_STATIC_DRAW)
        else if (buffer is DoubleBuffer)
            GL33.glBufferData(
                GL33.GL_ARRAY_BUFFER,
                buffer as DoubleBuffer,
                GL33.GL_STATIC_DRAW)
        else throw IllegalArgumentException("Unsupported buffer")
    }
}