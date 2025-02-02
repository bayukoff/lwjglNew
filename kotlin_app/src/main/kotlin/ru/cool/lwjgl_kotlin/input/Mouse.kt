package ru.cool.lwjgl_kotlin.input

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import ru.cool.lwjgl_kotlin.Config
import java.nio.DoubleBuffer

object Mouse {
    private val mousePosX: DoubleBuffer = BufferUtils.createDoubleBuffer(1)
    private val mousePosY: DoubleBuffer = BufferUtils.createDoubleBuffer(1)
    var lastX: Int = Config.WINDOW_WIDTH / 2
    var lastY: Int = Config.WINDOW_HEIGHT / 2
    var currentWindow: Long = 0
    var leftButtonPress: Boolean = false
    var leftButtonRelease: Boolean = true
    var rightButtonPress: Boolean = false
    var rightButtonRelease: Boolean = true

    fun isLeftButtonPress(): Boolean {
        leftButtonPress = GLFW.glfwGetMouseButton(currentWindow, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS
        if (leftButtonPress) leftButtonRelease = false
        return leftButtonPress
    }

    fun isLeftButtonRelease(): Boolean {
        if (!leftButtonPress && !leftButtonRelease) {
            leftButtonRelease = true
            return true
        }
        return false
    }

    fun isRightButtonPress(): Boolean {
        rightButtonPress = GLFW.glfwGetMouseButton(currentWindow, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS
        if (rightButtonPress) rightButtonRelease = false
        return rightButtonPress
    }

    fun isRightButtonRelease(): Boolean {
        if (!rightButtonPress && !rightButtonRelease) {
            rightButtonRelease = true
            return true
        }
        return false
    }

    fun handleInput(window: Long) {
        currentWindow = window
    }

    fun getMousePosX(): Float {
        GLFW.glfwGetCursorPos(currentWindow, mousePosX, null)
        val posX = mousePosX.get()
        mousePosX.clear()
        return posX.toFloat()
    }

    fun getMousePosY(): Float {
        GLFW.glfwGetCursorPos(currentWindow, null, mousePosY)
        val posY = mousePosY.get()
        mousePosY.clear()
        return posY.toFloat()
    }
}