package ru.cool.lwjgl_kotlin


import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack
import ru.cool.lwjgl_kotlin.geometry.QuadGeometry
import ru.cool.lwjgl_kotlin.geometry.TriangleGeometry
import ru.cool.lwjgl_kotlin.objects.Mesh
import org.lwjgl.glfw.GLFW as fw
import org.lwjgl.opengl.GL30 as gl

class Window(private val width: Int, private val height: Int, private val name: String) {

    var windowId: Long = 0

    private val resizeWindowCallback = GLFWFramebufferSizeCallbackI {
        window, width, height ->
            gl.glViewport(0,0,width,height)
    }

    fun createWindow() {
        if (!fw.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }
        fw.glfwDefaultWindowHints()
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
        GL.createCapabilities()
        fw.glfwShowWindow(windowId)
    }

    fun update() {
        val quad = Mesh(QuadGeometry())
        while (!fw.glfwWindowShouldClose(windowId)) {
            gl.glClear(gl.GL_COLOR_BUFFER_BIT or gl.GL_DEPTH_BUFFER_BIT)
            gl.glClearColor(1f, 0f, 0f, 1f)
            MeshRenderer.drawMesh(quad)
            MeshRenderer.draw()
            fw.glfwSwapBuffers(windowId)
            fw.glfwPollEvents()
        }
        fw.glfwSetFramebufferSizeCallback(windowId, resizeWindowCallback)
    }

}