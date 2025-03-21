package ru.cool.lwjgl_kotlin.objects.camera

import org.joml.Matrix4f
import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.objects.MovingObject
import ru.cool.lwjgl_kotlin.*

class PerspectiveCamera(
    var fov: Float,
    var aspect: Float,
    var zNear: Float,
    var zFar: Float,
    var position: Vector3f): MovingObject {
    val upVector = Vector3f(0f,1f,0f)
    var prespectiveMatrix = Matrix4f().perspective(fov, aspect, zNear, zFar)
    var direction = Vector3f(0f,0f,3f)
    var viewMatrix = Matrix4f().lookAt(position, direction, Vector3f(0f, 1f, 0f))
    var cameraRotationPitch = 0f
    var cameraRotationYaw = 0f

    constructor(fov: Float,
                aspect: Float,
                zNear: Float,
                zFar: Float
    ): this(fov,aspect,zNear,zFar, Vector3f())

    fun setPos(vec: Vector3f){
        position = vec
        updateMatrix()
    }

    fun updatePerspective(width: Int, height: Int){
        prespectiveMatrix = Matrix4f().perspective(fov, (width/height).toFloat(), zNear, zFar)
    }

    override fun move(vec: Vector3f) {
        position.add(vec)
        updateMatrix()
    }

    override fun move(x: Float, y: Float, z: Float) {
        position.add(x,y,z)
        updateMatrix()
    }

    override fun updateMatrix() {
        val newDirection = Vector3f(position).add(Vector3f(direction).normalize())
        viewMatrix = Matrix4f().lookAt(position, newDirection, upVector)
        MeshRenderer.shaderProgram.setUniformMatrix4f("u_view", viewMatrix)
        MeshRenderer.shaderProgram.setUniformVector3f("u_cameraPosition", position)
    }




}