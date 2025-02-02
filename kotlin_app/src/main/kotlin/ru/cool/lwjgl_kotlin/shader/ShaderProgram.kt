package ru.cool.lwjgl_kotlin.shader

import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack

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

    fun setUniformMatrix4f(name: String, mat: Matrix4f){
        val uniformLocation = getUniformLocation(name)
        MemoryStack.stackPush().use { stack ->
            val data = stack.mallocFloat(16)
            mat.get(data).flip()
            GL30.glUniformMatrix4fv(uniformLocation, false, data)
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

    fun getUniformLocation(name: String): Int{
        val uniformLocation = GL30.glGetUniformLocation(programID, name)
        if (uniformLocation == -1)
            throw Exception("Не удается найти Uniform!")
        return uniformLocation
    }
}