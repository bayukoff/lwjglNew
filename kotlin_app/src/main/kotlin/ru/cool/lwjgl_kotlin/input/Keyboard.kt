package ru.cool.lwjgl_kotlin.input

import org.lwjgl.glfw.GLFW

object Keyboard {
    var currentWindow: Long = 0

    fun isButtonPress(pressedKey: Buttons): Boolean {
        return GLFW.glfwGetKey(currentWindow, pressedKey.keyId) == GLFW.GLFW_PRESS
    }

    fun isButtonRelease(releaseKey: Buttons): Boolean {
        return GLFW.glfwGetKey(currentWindow, releaseKey.keyId) == GLFW.GLFW_RELEASE
    }

    fun handleInput(window: Long) {
        currentWindow = window
    }
}