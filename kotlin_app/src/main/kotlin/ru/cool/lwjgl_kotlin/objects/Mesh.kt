package ru.cool.lwjgl_kotlin.objects

import org.lwjgl.opengl.GL30
import ru.cool.lwjgl_kotlin.geometry.Geometry
import ru.cool.lwjgl_kotlin.glObjects.EBO
import ru.cool.lwjgl_kotlin.glObjects.VAO
import ru.cool.lwjgl_kotlin.glObjects.VBO

class Mesh(var geometry: Geometry) {
    private val vbo = VBO()
    private val ebo = EBO()
    private val vao = VAO()
    init {
        vao.bindVAO()
        vbo.bindBuffer()
        vbo.putDataToBuffer(geometry.vertices)
        ebo.bindBuffer()
        ebo.putDataToBuffer(geometry.indices)
        vao.setVertexAttribute(
            0,
            3,
            3 * Float.SIZE_BYTES,
            0)
        vbo.unbindBuffer()
//        ebo.unbindBuffer()
        vao.unbindVAO()
    }

    fun draw(){
        vao.bindVAO()
        vao.enableAttribute(0)
        GL30.glDrawElements(GL30.GL_TRIANGLES, geometry.indices.limit(), GL30.GL_UNSIGNED_INT, 0)
        vao.disableAttribute(0)
        vao.unbindVAO()
    }
}