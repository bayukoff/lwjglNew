package ru.cool.lwjgl_kotlin.geometry

import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil
import ru.cool.lwjgl_kotlin.glObjects.EBO
import ru.cool.lwjgl_kotlin.glObjects.VAO
import ru.cool.lwjgl_kotlin.glObjects.VBO
import java.nio.FloatBuffer
import java.nio.IntBuffer

abstract class Geometry private constructor(
    ) {
    val vbo = VBO()
    val ebo = EBO()
    val vao = VAO()
    open var hasNormal = false;
    open var hasUV = false;
    open lateinit var vertices: FloatBuffer
    open lateinit var indices: IntBuffer

    constructor(
        vertices: FloatBuffer,
        indices: IntBuffer,
        hasUV: Boolean,
        hasNormal: Boolean
    ) : this() {
        this.vertices = vertices
        this.indices = indices
        this.hasNormal = hasNormal
        this.hasUV = hasUV
        init()
    }

    fun init(){
        vao.bind()
        vbo.bindBuffer()
        vbo.putDataToBuffer(vertices)
        ebo.bindBuffer()
        ebo.putDataToBuffer(indices)
        MemoryUtil.memFree(vertices)
        MemoryUtil.memFree(indices)
        vao.setVertexAttribute(
            0,
            3,
            if(hasUV && hasNormal)
                8 * Float.SIZE_BYTES
            else if (hasUV)
                5 * Float.SIZE_BYTES
            else if (hasNormal)
                6 * Float.SIZE_BYTES
            else
                3 * Float.SIZE_BYTES,
            0)
        if(hasNormal)
            vao.setVertexAttribute(
                1,
                3,
                8 * Float.SIZE_BYTES,
                3 * Float.SIZE_BYTES)
        if(hasUV)
            vao.setVertexAttribute(
                2,
                2,
                8 * Float.SIZE_BYTES,
                6 * Float.SIZE_BYTES
            )
        vao.unbind()
    }
}