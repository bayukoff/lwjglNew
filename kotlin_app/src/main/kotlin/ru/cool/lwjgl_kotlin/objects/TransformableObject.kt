package ru.cool.lwjgl_kotlin.objects

import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

abstract class TransformableObject {
    open var startMatrix: Matrix4f? = null
    var position: Vector3f = Vector3f()
    var rotation: Quaternionf = Quaternionf()
    var scale: Vector3f = Vector3f(1f)

    open fun translate(x: Float, y: Float, z: Float){
        position.set(Vector3f(x,y,z))
    }

    open fun translate(vec: Vector3f){
        position.set(vec)
    }

    open fun scale(x: Float, y: Float, z: Float){
        scale.set(Vector3f(x,y,z))
    }

    open fun scale(vec: Vector3f){
        scale.set(vec)
    }

    open fun rotate(angle: Float, x: Float, y: Float, z: Float) {
        rotation.set(Quaternionf().fromAxisAngleDeg(x,y,z,angle))
    }

    open fun rotate(angle: Float, vec: Vector3f) {
        rotation.set(Quaternionf().fromAxisAngleDeg(vec,angle))
    }

    open fun rotate(q: Quaternionf) {
        rotation.set(q)
    }

    open fun setMatrix(transformMatrix: Matrix4f){
        startMatrix = transformMatrix
        transformMatrix.getTranslation(position)
        transformMatrix.getScale(scale)
        transformMatrix.getNormalizedRotation(rotation)
    }

    abstract fun updateMatrix()
}
