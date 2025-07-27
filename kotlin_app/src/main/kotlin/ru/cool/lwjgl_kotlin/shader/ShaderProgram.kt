package ru.cool.lwjgl_kotlin.shader

import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class ShaderProgram(val vertexShader: Shader, val fragmentShader: Shader) {
    var programID = GL30.glCreateProgram()

    fun createProgram(): ShaderProgram {
        if (vertexShader.shaderID or fragmentShader.shaderID == 0) println("Some Shader is not initialized!")
        GL30.glAttachShader(programID, vertexShader.shaderID)
        GL30.glAttachShader(programID, fragmentShader.shaderID)
        GL30.glLinkProgram(programID)
        GL30.glDeleteShader(vertexShader.shaderID)
        GL30.glDeleteShader(fragmentShader.shaderID)
        return this
    }

    fun bindProgram() {
        GL30.glUseProgram(programID)
    }

    fun unbindProgram() {
        GL30.glUseProgram(0)
    }

    fun setUniformBoolean(name: String, value: Boolean){
        val uniformLocation = getUniformLocation(name)
        GL30.glUniform1i(uniformLocation, if(value) 1 else 0)
    }

    fun setUniformFloat(name: String, value: Float){
        val uniformLocation = getUniformLocation(name)
        GL30.glUniform1f(uniformLocation, value)

    }

    fun setUniformMatrix4f(name: String, mat: Matrix4f){
        val uniformLocation = getUniformLocation(name)
        MemoryStack.stackPush().use { stack ->
            val data = stack.mallocFloat(16)
            mat.get(data)
            GL30.glUniformMatrix4fv(uniformLocation, false, data)
        }
    }

    fun setUniformMatrix4fv(name: String, matrices: List<Matrix4f>) {
        val location = getUniformLocation(name)
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(matrices.size * 16)
            for ((i, matrix) in matrices.withIndex()) {
                matrix.get(i * 16, buffer)
            }
            GL30.glUniformMatrix4fv(location, false, buffer)
        }
    }

    fun setUniformMatrix3f(name: String, mat: Matrix3f){
        val uniformLocation = getUniformLocation(name)
        MemoryStack.stackPush().use { stack ->
            val data = stack.mallocFloat(9)
            mat.get(data)
            GL30.glUniformMatrix3fv(uniformLocation, false, data)
        }
    }

    fun setUniformVector3f(name: String, vec: Vector3f){
        val uniformLocation = getUniformLocation(name)
        GL30.glUniform3f(uniformLocation, vec.x, vec.y, vec.z)
    }

    fun setUniformVector4f(name: String, vec: Vector4f){
        val uniformLocation = getUniformLocation(name)
        GL30.glUniform4f(uniformLocation, vec.x, vec.y, vec.z, vec.w)
    }

    fun getUniformMatrix4f(name: String): Matrix4f{
        MemoryStack.stackPush().use {
            val matrix = it.mallocFloat(16)
            GL30.glGetUniformfv(programID, getUniformLocation(name), matrix)
            return Matrix4f(matrix)
        }
    }

    fun getUniformMatrix4fv(name: String, count: Int): Array<Matrix4f>{
        val first = getUniformLocation("$name[0]")
        MemoryStack.stackPush().use { stack ->
            val data = stack.mallocFloat(count * 16)
            GL30.glGetUniformfv(programID, first, data)
            val matrices = Array(count) { Matrix4f() }
            for (i in 0 until count) {
                val start = i * 16
                val matBuffer = FloatArray(16)
                for (j in 0 until 16) {
                    matBuffer[j] = data[start + j]
                }
                matrices[i].set(matBuffer)
            }
            return matrices
        }
    }

    fun getUniformLocation(name: String): Int{
        val uniformLocation = GL30.glGetUniformLocation(programID, name)
        if (uniformLocation == -1)
            throw Exception("Не удается найти Uniform! $name")
        return uniformLocation
    }
}