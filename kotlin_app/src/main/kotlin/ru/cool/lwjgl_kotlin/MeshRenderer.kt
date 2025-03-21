package ru.cool.lwjgl_kotlin

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL33
import ru.cool.lwjgl_kotlin.input.controllers.CameraController
import ru.cool.lwjgl_kotlin.objects.Mesh
import ru.cool.lwjgl_kotlin.objects.camera.PerspectiveCamera
import ru.cool.lwjgl_kotlin.shader.Shader
import ru.cool.lwjgl_kotlin.shader.ShaderProgram
import ru.cool.lwjgl_kotlin.shader.ShaderType
import ru.cool.lwjgl_kotlin.material.TextureMaterial

object MeshRenderer {

    private val meshes: MutableList<Mesh> = mutableListOf()
    private val vertexShader = Shader("/shaders/vertexShader.vert", ShaderType.VERTEX_SHADER).compileShader()
    private val fragmentShader = Shader("/shaders/fragmentShader.frag", ShaderType.FRAGMENT_SHADER).compileShader()
    var shaderProgram: ShaderProgram = ShaderProgram(vertexShader, fragmentShader).createProgram()
    var camera: PerspectiveCamera = PerspectiveCamera(20f,
        (Config.WINDOW_WIDTH.toFloat() / Config.WINDOW_HEIGHT.toFloat()),
        0.01f,
        100f,
        Vector3f(0f,0f,3f)).also {
            shaderProgram.bindProgram()
            shaderProgram.setUniformMatrix4f("u_model", Matrix4f())
            shaderProgram.setUniformMatrix4f("u_projection", it.prespectiveMatrix)
            shaderProgram.setUniformMatrix4f("u_view", it.viewMatrix)
            shaderProgram.unbindProgram()
    }
    var cameraController = CameraController(camera, 5f)

    fun drawMesh(mesh: Mesh){
        if (!meshes.contains(mesh)) {
            meshes.add(mesh)
        }
    }

    fun beginDraw() {
        shaderProgram.bindProgram()
        cameraController.invoke()

    }
    fun draw(){
        for (mesh in meshes){
            val geometry = mesh.geometry
            val vao = geometry!!.vao
            val material = mesh.material
            vao.bind()
            if (material is TextureMaterial) {
                material.texture.bindTexture()
            }else{
                GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0)
            }
            mesh.material!!.applyMaterial()
            mesh.updateMatrix()
            GL33.glDrawElements(GL33.GL_TRIANGLES, geometry.indices.limit(), GL33.GL_UNSIGNED_INT, 0)
            if (material is TextureMaterial)
                material.texture.unbindTexture()
            vao.unbind()
        }
        shaderProgram.unbindProgram()
    }

}