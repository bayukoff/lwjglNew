package ru.cool.lwjgl_kotlin.loaders

import org.lwjgl.BufferUtils
import ru.cool.lwjgl_kotlin.geometry.CustomGeometry
import ru.cool.lwjgl_kotlin.geometry.Geometry
import java.io.InputStreamReader
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.DoubleBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.LongBuffer
import java.nio.ShortBuffer

object ObjLoader {

    fun loadModel(path: String): Geometry{
        val modelStream = InputStreamReader(javaClass.getResourceAsStream(path)!!)
        val verticesArray = mutableListOf<Vertex>()
        val uvArray = mutableListOf<UV>()
        val normalArray = mutableListOf<Normal>()
        val facesArray = mutableListOf<Face>()
        val lines = modelStream.readLines()
        var hasUV = false
        var hasNormal = false
        for (line in lines){
            if (line.startsWith(DataType.VERTEX.data)) {
                val vertex = parseVertex(line)
                verticesArray.add(vertex)
            }
            if(line.startsWith(DataType.UV.data)){
                hasUV = true
                val uv = parseUV(line)
                uvArray.add(uv!!)
            }
            if (line.startsWith(DataType.NORMAL.data)){
                hasNormal = true
                val normal = parseNormal(line)
                normalArray.add(normal!!)
            }
            if (line.startsWith(DataType.FACE.data)) {
                val face = parseFace(line)
                facesArray.add(face)
            }
        }
        val data = prepareDataToVBO(verticesArray, uvArray, normalArray, facesArray)
        val indices = createIndexBuffer(data)

        return CustomGeometry(data, indices, hasUV, hasNormal)
    }

    private fun createIndexBuffer(vertices: FloatBuffer): IntBuffer{
        val indexBuffer = BufferUtils.createIntBuffer(vertices.capacity() / 5)
        for (i in 0 until vertices.capacity() / 5)
            indexBuffer.put(i)
        indexBuffer.flip()
        return indexBuffer
    }

    private fun prepareDataToVBO(vertices: List<Vertex>, uvCoords: List<UV>, normalsList: List<Normal>, faces: List<Face>): FloatBuffer{
        val finalVertices = mutableListOf<Vertex>()
        val finalUV = mutableListOf<UV>()
        val finalNormals = mutableListOf<Normal>()
        var haveTexture = true
        var haveNormal = true
        for (face in faces){
            for (vertexIndex in face.vertices){
                finalVertices.add(vertices[vertexIndex - 1])
            }
            if (face.isThereTexture()) {
                for (uvIndex in face.uv){
                    finalUV.add(uvCoords[uvIndex - 1])
                }
            }else haveTexture = false
            if (face.isThereNormal()){
                for (normalIndex in face.normals){
                    finalNormals.add(normalsList[normalIndex - 1])
                }
            }else haveNormal = false
        }
        val dataToVBO = BufferUtils.createFloatBuffer(
            if (haveTexture && !haveNormal)
                finalVertices.size * 5
            else if (!haveTexture && haveNormal)
                finalVertices.size * 6
            else if (haveNormal && haveTexture)
                finalVertices.size * 8
            else finalVertices.size * 3
        )
        for (i in 0 until finalVertices.size){
            val vertex = finalVertices[i]
            dataToVBO.put(vertex.x)
            dataToVBO.put(vertex.y)
            dataToVBO.put(vertex.z)
            if (haveNormal){
                val normal = finalNormals[i]
                dataToVBO.put(normal.x)
                dataToVBO.put(normal.y)
                dataToVBO.put(normal.z)
            }
            if (haveTexture) {
                val uv = finalUV[i]
                dataToVBO.put(uv.u)
                dataToVBO.put(uv.v)
            }
        }
        dataToVBO.flip()
        return dataToVBO
    }

    private fun parseNormal(line: String): Normal?{
        val normal = Normal(0f,0f,0f)
        if(line.startsWith("vn ", true)){
            val dataStr = line.split("vn ")[1].split(" ")
            if (dataStr.isEmpty())
                throw Exception("Не удалось извлечь нормали!")
            val x = dataStr.get(0).toFloat()
            val y = dataStr.get(1).toFloat()
            val z = dataStr.get(2).toFloat()
            normal.x = x
            normal.y = y
            normal.z = z
        }else{
            return null
        }
        return normal
    }

    private fun parseUV(line: String): UV?{
        val uv = UV(0f,0f)
        if(line.startsWith("vt ", true)){
            val dataStr = line.split("vt ")[1].split(" ")
            if (dataStr.isEmpty())
                throw Exception("Не удалось извлечь текстурный координаты!")
            val u = dataStr.get(0).toFloat()
            val v = dataStr.get(1).toFloat()
            uv.u = u
            uv.v = v
        }else{
            return null
        }
        return uv
    }

    private fun parseVertex(line: String): Vertex{
        val vertex = Vertex(0f,0f,0f)
        if(line.startsWith("v ", true)){
            val dataStr = line.split("v ")[1].split(" ")
            if (dataStr.isEmpty())
                throw Exception("Не удалось извлечь вершины!")
            val x = dataStr.get(0).toFloat()
            val y = dataStr.get(1).toFloat()
            val z = dataStr.get(2).toFloat()
            vertex.x = x
            vertex.y = y
            vertex.z = z
        }
        return vertex
    }

    private fun parseFace(line: String): Face{
        val face = Face(IntArray(3), IntArray(3), IntArray(3))
        if (line.startsWith(DataType.FACE.data)){
            var reg = Regex("(?<=\\s)\\d+(?=/)")
            var data = reg.findAll(line)
            face.vertices[0] = data.elementAt(0).value.toInt()
            face.vertices[1] = data.elementAt(1).value.toInt()
            face.vertices[2] = data.elementAt(2).value.toInt()
            reg = Regex("(?<=/)\\d+(?=/)")
            data = reg.findAll(line)
            if (data.count() != 0){
                face.uv[0] = data.elementAt(0).value.toInt()
                face.uv[1] = data.elementAt(1).value.toInt()
                face.uv[2] = data.elementAt(2).value.toInt()
            }
            reg = Regex("(?<=/)\\d+(?=\\s|$)")
            data = reg.findAll(line)
            if (data.count() != 0){
                face.normals[0] = data.elementAt(0).value.toInt()
                face.normals[1] = data.elementAt(1).value.toInt()
                face.normals[2] = data.elementAt(2).value.toInt()
            }

        }
        return face
    }

    internal enum class DataType(val data: String){
        VERTEX("v "), UV("vt "), NORMAL("vn "), FACE("f ")
    }
}