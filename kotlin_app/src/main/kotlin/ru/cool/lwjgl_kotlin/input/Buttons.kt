package ru.cool.lwjgl_kotlin.input

import org.lwjgl.glfw.GLFW

enum class Buttons(keyId: Int) {
    ESCAPE(GLFW.GLFW_KEY_ESCAPE);

    var keyId = keyId

}