package ru.cool.lwjgl_kotlin.shader

import org.lwjgl.opengl.GL30

enum class ShaderType(type: Int) {
    VERTEX_SHADER(GL30.GL_VERTEX_SHADER),
    FRAGMENT_SHADER(GL30.GL_FRAGMENT_SHADER);

    val type = type
}