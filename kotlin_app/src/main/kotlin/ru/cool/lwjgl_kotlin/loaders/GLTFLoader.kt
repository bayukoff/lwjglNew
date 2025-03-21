package ru.cool.lwjgl_kotlin.loaders

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL33
import ru.cool.lwjgl_kotlin.geometry.Geometry
import ru.cool.lwjgl_kotlin.objects.Mesh
import ru.cool.lwjgl_kotlin.utils.Buffers
import java.awt.geom.Arc2D
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer

object GLTFLoader {

    private var binaryDataOffset = 0
    private val GLB_HEADER_OFFSET = 12
    private val JSON_CHUNK_LENGTH_OFFSET = 16

    fun loadModel(path: String){
        var inputStream = javaClass.getResourceAsStream(path)
        if (inputStream == null)
            throw FileNotFoundException("Файл модели не найден!")
        var jsonObject: JsonObject? = null
        inputStream.use { ins ->
            val json = parseJsonData(ins)
            jsonObject = JsonParser.parseString(json).asJsonObject
            val meshesList = getMeshesWithInfo(jsonObject)
            val binaryDataSize = getBinaryDataSize(ins)// После парсинга получаю размер бинарных данных
            ins.skip(4)  // Пропускаю BIN\0
            val binaryDataArray = ByteArray(binaryDataSize)
            inputStream.read(binaryDataArray)
            val binaryDataBuffer = ByteBuffer.wrap(binaryDataArray).order(ByteOrder.LITTLE_ENDIAN)
            for(meshIndex in 0 until meshesList.size){
                val accessors = getMeshAccessors(jsonObject, meshIndex)
                var indices: Buffer? = null
                var pnt: Buffer? = null
                var vertices: FloatArray? = null
                var normals: FloatArray? = null
                for(accessorIndex in 0 until accessors.size){
                    val accessorId = accessors[accessorIndex]
                    if (accessorId == -1)
                        continue
                    val accessor = getAccessorData(accessorId, jsonObject)
                    val bufferView = getBufferView(accessor.bufferView, jsonObject)
                    if (accessor.name != "POSITION" &&
                        accessor.name != "NORMAL" &&
                        accessor.name != "TEXCOORD_0" &&
                        indices == null)    //Читаю индексы
                    {
                        indices = getIndices(accessor, bufferView, binaryDataBuffer)
                        Buffers.showBufferData(indices)
                    }
                    println(binaryDataBuffer.position())
                    if (accessor.name == "POSITION"){
                        vertices = getVertices(accessor, bufferView, binaryDataBuffer)
                    }
                    if (accessor.name == "NORMAL"){
                        normals = getNormals(accessor, bufferView, binaryDataBuffer)
                    }
                }
                val vbo = bufferForVBO(vertices, normals, textures)
            }

        }
    }

    private fun bufferForVBO(vertices: FloatArray, normals: FloatArray, textures: FloatArray?): FloatBuffer{
        if (textures == null)
            return mergeData(vertices, normals, 3, 3)
        return mergeData()
    }

    private fun mergeData(first: FloatArray,
                          second: FloatArray,
                          firstElementComponents: Int,
                          secondElementComponents: Int): FloatBuffer
    {
        val buffer = FloatArray(first.size + second.size)
        for (i in 0..buffer.size / 2){
            buffer.put(first, i * firstElementComponents * 2, firstElementComponents)
            buffer.put(second, i * 6 + secondElementComponents, secondElementComponents)
        }
        Buffers.showBufferData(buffer)
        return buffer
    }

    private fun getBinaryDataSize(ins: InputStream): Int{
        var size = ByteArray(4)
        ins.read(size)
        return bytesToDecimal(size)
    }

    private fun getNormals(accessor: Accessor, bufferView: BufferView, binaryDataBuffer: ByteBuffer): FloatArray{
        val bytesStride = bufferView.byteStride
        val offset = bufferView.byteOffset + accessor.byteOffset + 12  //Смещение после начала данных
        val normals = FloatArray(accessor.count * 3)
        for (i in 0 until accessor.count){
            binaryDataBuffer.position(offset + (i * bytesStride / 2))
            normals[i * 3] = binaryDataBuffer.float
            normals[i * 3 + 1] = binaryDataBuffer.float
            normals[i * 3 + 2] = binaryDataBuffer.float
            println(i)
        }
        println(normals.contentToString())
        return normals
    }

    private fun getVertices(accessor: Accessor, bufferView: BufferView, binaryDataBuffer: ByteBuffer): FloatArray{
        val bytesStride = bufferView.byteStride
        val offset = bufferView.byteOffset + accessor.byteOffset  //Смещение после начала данных
        val vertices = FloatArray(accessor.count * 3)
        for (i in 0 until accessor.count){
            binaryDataBuffer.position(offset + (i * bytesStride / 2))
            vertices[i * 3] = binaryDataBuffer.float
            vertices[i * 3 + 1] = binaryDataBuffer.float
            vertices[i * 3 + 2] = binaryDataBuffer.float
            println(i)
        }
        println(vertices.contentToString())
        return vertices
    }

    //TODO Нужно понизить версию jvm обратно до 8, и изменить метод.
    private fun getIndices(accessor: Accessor, bufferView: BufferView, binaryData: ByteBuffer): ShortBuffer{
        val indices = BufferUtils.createShortBuffer(accessor.count)
        indices.put(0, binaryData.asShortBuffer(), bufferView.byteOffset + accessor.byteOffset, accessor.count - 1)
        return indices
    }

    private fun getBufferView(bufferViewId: Int, jsonObject: JsonObject): BufferView{
        val bufferViewJson = jsonObject.get("bufferViews")
            .asJsonArray.get(bufferViewId).asJsonObject
        val bufferView = BufferView(
            bufferViewJson.get("buffer").asInt,
            bufferViewJson.get("byteOffset").asInt,
            bufferViewJson.get("byteLength").asInt,
            bufferViewJson.get("byteStride")?.asInt ?: -1,
            bufferViewJson.get("target").asInt
        )
        return bufferView
    }

    private fun getBufferType(componentType: Int): BufferType{
        if (componentType == GL33.GL_UNSIGNED_SHORT || componentType == GL33.GL_UNSIGNED_INT) {
            return BufferType.INT
        }
        if(componentType == GL33.GL_FLOAT){
            return BufferType.FLOAT
        }
        throw Exception("Неопределенный тип данных!")
    }

    private fun getAccessorData(accessorId: Int, jsonObject: JsonObject): Accessor{
        val bufferData = jsonObject.get("accessors").asJsonArray.get(accessorId).asJsonObject
        val bufferView = bufferData.get("bufferView").asInt
        val byteOffset = bufferData.get("byteOffset").asInt
        val componentType = bufferData.get("componentType").asInt
        val count = bufferData.get("count").asInt
        val type = bufferData.get("type").asString
        val name = bufferData.get("name")?.asString ?: "None"
        return Accessor(bufferView, byteOffset, componentType, count, type, name)
    }

    private fun getMeshAccessors(jsonObject: JsonObject, meshId: Int): IntArray{
        val mesh = jsonObject.get("meshes").asJsonArray.get(meshId).asJsonObject
        val primitives = mesh.get("primitives").asJsonArray.get(0).asJsonObject
        val attributes = primitives.get("attributes").asJsonObject
        val indices = primitives.get("indices").asInt
        val position = attributes.get("POSITION").asInt
        val normal = attributes.get("NORMAL").asInt
        val texcoord = attributes.get("TEXCOORD_0")?.asInt ?: -1
        return intArrayOf(indices, position, normal, texcoord)
    }

    private fun getMeshesWithInfo(jsonObject: JsonObject): List<Mesh>{
        val meshes = jsonObject.get("nodes").asJsonArray
        val meshesList = ArrayList<Mesh>(meshes.size())
        for (mesh in meshes){
            val meshObj = Mesh()
            val meshJson = mesh.asJsonObject
            val meshName = meshJson.get("name").asString
            if (meshName.equals("Camera"))
                continue
            meshObj.meshName = meshName
            meshObj.meshId = meshJson.get("mesh").asInt
            val translation = meshJson.get("translation").asJsonArray
            val rotation = meshJson.get("rotation").asJsonArray
            val scale = meshJson.get("scale").asJsonArray
            var x = translation.get(0).asFloat
            var y = translation.get(1).asFloat
            var z = translation.get(2).asFloat
            meshObj.translate(x,y,z)
            val angle = rotation.get(0).asDouble
            x = rotation.get(1).asFloat
            y = rotation.get(2).asFloat
            z = rotation.get(3).asFloat
            meshObj.rotate(angle.toFloat(), x,y,z)
            x = scale.get(0).asFloat
            y = scale.get(1).asFloat
            z = scale.get(2).asFloat
            meshObj.scale(x,y,z)
            meshesList.add(meshObj)
        }
        println(meshesList)
        return meshesList
    }

    /**
     * Парсит json, пропуская GLB-header, заканчия фигурной скобкой JSON чанка
     */
    private fun parseJsonData(inputStream: InputStream): String{
        var json = ""
        inputStream.skip(12)
        val chunkSizeArray = ByteArray(5)
        inputStream.read(chunkSizeArray)
        val decimalSize = bytesToDecimal(chunkSizeArray)
        binaryDataOffset = 20 + decimalSize + 8
        inputStream.skip(3)
        val jsonBytes = ByteArray(decimalSize)
        inputStream.read(jsonBytes, 0, decimalSize)
        json = String(jsonBytes, Charsets.UTF_8)
        return json
    }

    private fun bytesToDecimal(bytes: ByteArray): Int{
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).int
    }

    enum class DataType(val typeName: String){
        VEC3("VEC3"), SCALAR("SCALAR")
    }

    enum class BufferType{
        FLOAT, INT, BYTE, DOUBLE
    }
}