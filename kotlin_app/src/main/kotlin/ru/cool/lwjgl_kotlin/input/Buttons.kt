package ru.cool.lwjgl_kotlin.input

import org.lwjgl.glfw.GLFW

enum class Buttons(keyId: Int) {
    ESCAPE(GLFW.GLFW_KEY_ESCAPE),
    W(GLFW.GLFW_KEY_W),
    A(GLFW.GLFW_KEY_A),
    S(GLFW.GLFW_KEY_S),
    D(GLFW.GLFW_KEY_D),
    R(GLFW.GLFW_KEY_R),
    F(GLFW.GLFW_KEY_F),
    SPACE(GLFW.GLFW_KEY_SPACE),
    LEFT_CTR(GLFW.GLFW_KEY_LEFT_CONTROL);

    var keyId = keyId

}