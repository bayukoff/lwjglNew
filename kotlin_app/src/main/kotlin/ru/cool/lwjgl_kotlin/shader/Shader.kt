package ru.cool.lwjgl_kotlin.shader

import org.lwjgl.opengl.GL30
import java.io.File
import java.io.InputStreamReader

class Shader(shaderProgramPath: String, type: ShaderType) {
    private var shaderCode: String
    var shaderID = GL30.glCreateShader(type.type)

    init{
        val reader = InputStreamReader(javaClass.getResourceAsStream(shaderProgramPath)!!)
        shaderCode = reader.readText()
    }

    fun compileShader(): Shader{
        GL30.glShaderSource(shaderID, shaderCode)
        GL30.glCompileShader(shaderID)
        if (isShaderCompile(shaderID)){
            throw Exception("Ошибка компиляции шейдера!")
        }
        return this
    }

    private fun isShaderCompile(shaderIndex: Int): Boolean {
        val shaderStatusCode = GL30.glGetShaderi(shaderIndex, GL30.GL_COMPILE_STATUS)
        val shaderLog = GL30.glGetShaderInfoLog(shaderIndex)
        if (shaderLog.isNotEmpty()) {
            println("Shader compilation log: $shaderLog")
        }
        return shaderStatusCode != 1
    }
}