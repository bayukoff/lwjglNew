package ru.cool.lwjgl_kotlin

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL33
import ru.cool.lwjgl_kotlin.geometry.BoneGeometry
import ru.cool.lwjgl_kotlin.input.controllers.CameraController
import ru.cool.lwjgl_kotlin.objects.Mesh
import ru.cool.lwjgl_kotlin.objects.camera.PerspectiveCamera
import ru.cool.lwjgl_kotlin.shader.Shader
import ru.cool.lwjgl_kotlin.shader.ShaderProgram
import ru.cool.lwjgl_kotlin.shader.ShaderType
import ru.cool.lwjgl_kotlin.material.TextureMaterial
import ru.cool.lwjgl_kotlin.objects.Model
import ru.cool.lwjgl_kotlin.objects.SceneObject
import ru.cool.lwjgl_kotlin.objects.SceneObject3D
import ru.cool.lwjgl_kotlin.utils.Time

object MeshRenderer {

    private val objects: MutableList<SceneObject3D> = mutableListOf()
    private val objectsBatch: MutableList<Array<SceneObject>> = mutableListOf()
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

    fun addObjectToDraw(sceneObject: SceneObject3D) {
        if (!objects.contains(sceneObject))
            objects.add(sceneObject)
    }

    fun addObjectsToDraw(sceneObjects: Array<SceneObject>) {
        if (!objectsBatch.contains(sceneObjects)){
            objectsBatch.add(sceneObjects)
        }
    }

    fun beginDraw() {
        shaderProgram.bindProgram()
        cameraController.invoke()
    }

    private fun drawInstanced(){
        
    }

    private fun draw(sceneObject: SceneObject) {
        if (sceneObject is SceneObject3D){
            val objectGeometry = sceneObject.geometry
            if (objectGeometry != null){
                for (geometry in objectGeometry) {
                    val vao = geometry.vao
                    val material = sceneObject.material
                    shaderProgram.setUniformBoolean("u_useSkinning", geometry is BoneGeometry)
                    vao.bind()
                    if (material != null){
                        if (material is TextureMaterial) {
                            material.texture.bindTexture()
                        } else {
                            GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0)
                        }
                        material.applyMaterial()
                    }
                    sceneObject.updateMatrix()
                    GL33.glDrawElements(GL33.GL_TRIANGLES, geometry.indices.size, GL33.GL_UNSIGNED_INT, 0)
                    if (material is TextureMaterial)
                        material.texture.unbindTexture()
                    vao.unbind()
                }
            }
        }
    }

    private fun drawChildren(children: Array<SceneObject>){
        for (child in children){
            draw(child)
            if (!child.children.isNullOrEmpty()){
                drawChildren(child.children!!)
            }
        }
    }

    fun draw(){
        for (sceneObject in objects) {
            draw(sceneObject)
            val sceneObjectChildren = sceneObject.children
            if (!sceneObjectChildren.isNullOrEmpty()){
                drawChildren(sceneObjectChildren)
            }
        }
        shaderProgram.unbindProgram()
    }

}