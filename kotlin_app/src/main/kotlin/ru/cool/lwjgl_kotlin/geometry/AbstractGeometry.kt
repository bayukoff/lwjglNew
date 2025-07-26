package ru.cool.lwjgl_kotlin.geometry

import ru.cool.lwjgl_kotlin.glObjects.EBO
import ru.cool.lwjgl_kotlin.glObjects.VAO
import ru.cool.lwjgl_kotlin.glObjects.VBO
import ru.cool.lwjgl_kotlin.loaders.types.Normal
import ru.cool.lwjgl_kotlin.loaders.types.UV
import ru.cool.lwjgl_kotlin.loaders.types.Vertex
import ru.cool.lwjgl_kotlin.utils.Buffers.memUse
import ru.cool.lwjgl_kotlin.utils.GeometryUtils
import ru.cool.lwjgl_kotlin.utils.VaoHelper

abstract class AbstractGeometry(
    open val vertices: Array<Vertex>,
    open val normals: Array<Normal>?,
    open val textureCoords: Array<UV>?,
    open val indices: IntArray) {

    val vbo = VBO()
    val vao = VAO()
    val ebo = EBO()

    open fun initBuffers() {
        vao.bind()
        vbo.bindBuffer()
        ebo.bindBuffer()
        GeometryUtils.buildBuffer(vertices, normals, textureCoords).memUse {
            vbo.putDataToBuffer(it)
        }
        ebo.bindBuffer()
        GeometryUtils.buildIndices(indices).memUse {
            ebo.putDataToBuffer(it)
        }
        VaoHelper.setVertexAttributes(vao, normals != null, textureCoords != null, false)
        vbo.unbindBuffer()
    }
}