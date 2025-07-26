package ru.cool.lwjgl_kotlin.utils

import org.lwjgl.assimp.AIFace
import org.lwjgl.assimp.AIMesh
import ru.cool.lwjgl_kotlin.loaders.types.Normal
import ru.cool.lwjgl_kotlin.loaders.types.UV
import ru.cool.lwjgl_kotlin.loaders.types.Vertex

object VertexDataHandler {

    fun getVertices(mesh: AIMesh): Array<Vertex> {
        val vertexBuffer = mesh.mVertices()
        val vertices = Array(mesh.mNumVertices()) { i ->
            val v = vertexBuffer[i]
            Vertex(v.x(), v.y(), v.z())
        }
        return vertices
    }

    fun getTexCoords(mesh: AIMesh): Array<UV>? {
        val texCoordsBuffer = mesh.mTextureCoords(0)
            ?: return null
        val texCoords = Array(mesh.mNumVertices()) { i ->
            val uv = texCoordsBuffer[i]
            UV(uv.x(), uv.y())
        }
        return texCoords
    }

    fun getNormals(mesh: AIMesh): Array<Normal>? {
        val normalsBuffer = mesh.mNormals() ?: return null
        val normals = Array(mesh.mNumVertices()) { i ->
            val n = normalsBuffer[i]
            Normal(n.x(), n.y(), n.z())
        }
        return normals
    }

    fun getIndices(mesh: AIMesh): IntArray {
        val numFaces = mesh.mNumFaces()
        val aiFaces = mesh.mFaces()
        var totalIndices = 0
        for (i in 0 until numFaces) {
            val face = AIFace.create(aiFaces[i].address())
            totalIndices += face.mNumIndices()
        }
        val indices = IntArray(totalIndices)
        var cursor = 0
        for (i in 0 until numFaces) {
            val face = AIFace.create(aiFaces[i].address())
            val indexBuffer = face.mIndices()
            for (j in 0 until face.mNumIndices()) {
                indices[cursor++] = indexBuffer[j]
            }
        }

        return indices
    }
}