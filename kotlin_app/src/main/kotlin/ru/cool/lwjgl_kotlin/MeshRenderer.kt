package ru.cool.lwjgl_kotlin

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30
import ru.cool.lwjgl_kotlin.objects.Mesh
import ru.cool.lwjgl_kotlin.objects.camera.PerspectiveCamera
import ru.cool.lwjgl_kotlin.shader.Shader
import ru.cool.lwjgl_kotlin.shader.ShaderProgram
import ru.cool.lwjgl_kotlin.shader.ShaderType

object MeshRenderer {

    private val meshes: MutableList<Mesh> = mutableListOf()
    private val vertexShader = Shader("/shaders/vertexShader.vert", ShaderType.VERTEX_SHADER).compileShader()
    private val fragmentShader = Shader("/shaders/fragmentShader.frag", ShaderType.FRAGMENT_SHADER).compileShader()
    var shaderProgram: ShaderProgram = ShaderProgram(vertexShader, fragmentShader).createProgram()
    var camera: PerspectiveCamera = PerspectiveCamera(40f,
        (Config.WINDOW_WIDTH.toFloat() / Config.WINDOW_HEIGHT.toFloat()),
        0.01f,
        100f,
        Vector3f(0f,0f,0f)).also {
//            shaderProgram.bindProgram()
//            shaderProgram.setUniformMatrix4f("projection", it.prespectiveMatrix)
//            shaderProgram.unbindProgram()
    }
    var viewMatrix = Matrix4f().lookAt(camera.position, Vector3f(0f, 0f, 3f), Vector3f(0f, 1f, 0f))

    fun drawMesh(mesh: Mesh){
        if (!meshes.contains(mesh))
            meshes.add(mesh)
    }

    fun draw(){
        shaderProgram.bindProgram()
        shaderProgram.setUniformMatrix4f("model", Matrix4f().translate(-0.2f, 0.0f,0.0f))
        Thread.sleep(700)

        for (mesh in meshes){
            mesh.draw()
        }
        shaderProgram.unbindProgram()
    }

}