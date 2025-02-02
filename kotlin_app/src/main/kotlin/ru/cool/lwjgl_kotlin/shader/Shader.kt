package ru.cool.lwjgl_kotlin.shader

import org.lwjgl.opengl.GL30
import java.io.File

class Shader(val shaderProgramPath: String, type: ShaderType) {
    private lateinit var shaderCode: String
    var shaderID = GL30.glCreateShader(type.type)

    init{
        val file = File(javaClass.getResource(shaderProgramPath).toURI())
        shaderCode = file.readText()
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