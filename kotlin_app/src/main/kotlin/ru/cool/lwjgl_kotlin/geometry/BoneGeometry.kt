package ru.cool.lwjgl_kotlin.geometry

import org.joml.Vector4f
import org.joml.Vector4i
import ru.cool.lwjgl_kotlin.loaders.types.Normal
import ru.cool.lwjgl_kotlin.loaders.types.UV
import ru.cool.lwjgl_kotlin.loaders.types.Vertex
import ru.cool.lwjgl_kotlin.utils.Buffers.memUse
import ru.cool.lwjgl_kotlin.utils.GeometryUtils
import ru.cool.lwjgl_kotlin.utils.VaoHelper

class BoneGeometry(
    override val vertices: Array<Vertex>,
    override val normals: Array<Normal>?,
    override val textureCoords: Array<UV>?,
    override val indices: IntArray,
    val boneIndices: Array<Vector4i>,
    val boneWeights: Array<Vector4f>
    ): AbstractGeometry(
        vertices, normals, textureCoords, indices
    ) {

    init {
        initBuffers()
    }

    override fun initBuffers(){
        vao.bind()
        vbo.bindBuffer()
        ebo.bindBuffer()
        GeometryUtils.buildBuffer(vertices, normals, textureCoords, boneIndices, boneWeights).memUse {
            vbo.putDataToBuffer(it)
        }
        ebo.bindBuffer()
        GeometryUtils.buildIndices(indices).memUse {
            ebo.putDataToBuffer(it)
        }
        VaoHelper.setVertexAttributes(vao, normals != null, textureCoords != null, true)
        vbo.unbindBuffer()
    }
}