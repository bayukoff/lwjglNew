package ru.cool.lwjgl_kotlin


import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack
import ru.cool.lwjgl_kotlin.input.Buttons
import ru.cool.lwjgl_kotlin.input.Keyboard
import ru.cool.lwjgl_kotlin.input.Mouse
import ru.cool.lwjgl_kotlin.utils.Time
import org.lwjgl.glfw.GLFW as fw
import org.lwjgl.opengl.GL33 as gl

class Window(private val width: Int, private val height: Int, private val name: String) {

    var windowId: Long = 0
    private var game: Game = Game()

    private val resizeWindowCallback = GLFWFramebufferSizeCallbackI {
        window, width, height ->
        gl.glViewport(0,0,width,height)
        MeshRenderer.camera.updatePerspective(width, height)
    }

    fun createWindow() {
        if (!fw.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }
        fw.glfwDefaultWindowHints()
        fw.glfwWindowHint(fw.GLFW_CONTEXT_VERSION_MAJOR, 3);
        fw.glfwWindowHint(fw.GLFW_CONTEXT_VERSION_MINOR, 3);
        fw.glfwWindowHint(fw.GLFW_OPENGL_PROFILE, fw.GLFW_OPENGL_CORE_PROFILE);
        fw.glfwWindowHint(fw.GLFW_VISIBLE, fw.GLFW_FALSE)
        fw.glfwWindowHint(fw.GLFW_RESIZABLE, fw.GLFW_TRUE)
        windowId = fw.glfwCreateWindow(width, height, name, 0, 0)
        if (windowId == 0L) {
            throw RuntimeException("Failed to create the GLFW window")
        }
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)
            fw.glfwGetWindowSize(windowId, pWidth, pHeight)
            val vidMode = fw.glfwGetVideoMode(fw.glfwGetPrimaryMonitor())!!
            fw.glfwSetWindowPos(
                windowId,
                (vidMode.width() - pWidth.get(0)) / 2,
                (vidMode.height() - pHeight.get(0)) / 2
            )
        }
        fw.glfwMakeContextCurrent(windowId)
        fw.glfwSetInputMode(windowId, fw.GLFW_CURSOR, fw.GLFW_CURSOR_DISABLED);
        GL.createCapabilities()
        println(GL11.glGetString(GL11.GL_VERSION))
        println(GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION))
        fw.glfwShowWindow(windowId)
        Keyboard.handleInput(windowId)
        Mouse.handleInput(windowId)
    }

    fun update() {
        game.create()

        while (!fw.glfwWindowShouldClose(windowId)) {
            if (Keyboard.isButtonPress(Buttons.ESCAPE)){
                fw.glfwTerminate()
                return
            }
            Time.prevTime = fw.glfwGetTime()
            game.update()
            gl.glDisable(gl.GL_DEPTH_TEST)
            fw.glfwSwapBuffers(windowId)
            fw.glfwPollEvents()
            Time.deltaTime = Time.prevTime - fw.glfwGetTime()
            fw.glfwSetFramebufferSizeCallback(windowId, resizeWindowCallback)
        }
    }
}