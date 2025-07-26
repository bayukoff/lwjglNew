package ru.cool.lwjgl_kotlin.utils

import org.joml.Vector4f
import org.joml.Vector4i
import org.lwjgl.system.MemoryUtil
import ru.cool.lwjgl_kotlin.loaders.types.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

object GeometryUtils {

    fun buildIndices(indices: IntArray): IntBuffer{
        val indicesBuffer = MemoryUtil.memAllocInt(indices.size)
        indices.forEach {
            indicesBuffer.put(it)
        }
        return indicesBuffer.flip()
    }

    fun buildBuffer(
        vertices: Array<Vertex>,
        normals: Array<Normal>?,
        textureCoords: Array<UV>?
    ): FloatBuffer{
        val floatsPerVertex = 3 + (if (normals != null) 3 else 0) + (if (textureCoords != null) 2 else 0)
        val buffer = MemoryUtil.memAllocFloat(vertices.size * floatsPerVertex)
        putDataToBuffer(buffer, vertices, normals, textureCoords)
        return buffer.flip()
    }

    fun buildBuffer(
        vertices: Array<Vertex>,
        normals: Array<Normal>?,
        textureCoords: Array<UV>?,
        boneIndices: Array<Vector4i>,
        boneWeights: Array<Vector4f>
    ): FloatBuffer {
        val floatsPerVertex = 3 + (if (normals != null) 3 else 0) + (if (textureCoords != null) 2 else 0) + 4 + 4
        val buffer = MemoryUtil.memAllocFloat(vertices.size * floatsPerVertex)
        putDataToBuffer(buffer, vertices, normals, textureCoords){ buf, index ->
            val indices = boneIndices[index]
            val weights = boneWeights[index]

            buf.put(indices.x.toFloat())
                .put(indices.y.toFloat())
                .put(indices.z.toFloat())
                .put(indices.w.toFloat())

            buf.put(weights.x)
                .put(weights.y)
                .put(weights.z)
                .put(weights.w)
        }
        buffer.flip()
        return buffer
    }

    private inline fun putDataToBuffer(
        buffer: FloatBuffer,
        vertices: Array<Vertex>,
        normals: Array<Normal>?,
        textureCoords: Array<UV>?,
        dataPutting: (FloatBuffer, Int) -> Unit = {_, _ -> })
    {
        for (v in vertices.indices){
            val vertex = vertices[v]
            val normal = normals?.get(v)
            val uv = textureCoords?.get(v)

            buffer.put(vertex.x).put(vertex.y).put(vertex.z)

            if (normal != null)
                buffer.put(normal.x).put(normal.y).put(normal.z)

            if (uv != null)
                buffer.put(uv.x).put(uv.y)

            dataPutting(buffer, v)
        }
    }
}
