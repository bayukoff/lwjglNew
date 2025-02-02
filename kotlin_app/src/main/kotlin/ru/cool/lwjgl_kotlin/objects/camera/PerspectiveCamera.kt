package ru.cool.lwjgl_kotlin.objects.camera

import org.joml.Matrix4f
import org.joml.Vector3f

class PerspectiveCamera(
    var fov: Float,
    var aspect: Float,
    var zNear: Float,
    var zFar: Float,
    var position: Vector3f) {

    val prespectiveMatrix = Matrix4f().perspective(fov, aspect, zNear, zFar)

    constructor(fov: Float,
                aspect: Float,
                zNear: Float,
                zFar: Float
    ): this(fov,aspect,zNear,zFar, Vector3f())



}