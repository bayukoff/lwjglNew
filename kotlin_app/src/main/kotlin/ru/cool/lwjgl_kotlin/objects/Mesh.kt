package ru.cool.lwjgl_kotlin.objects

import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import ru.cool.lwjgl_kotlin.MeshRenderer
import ru.cool.lwjgl_kotlin.geometry.Geometry
import ru.cool.lwjgl_kotlin.material.IMaterial

class Mesh(){

    var geometry: Geometry? = null
    var material: IMaterial? = null
    var transformationMatrix = Matrix4f()
    var meshName = "Mesh"
    var position = Vector3f(0f,0f,0f)
    var rotation = Vector4f(0f,0f,0f,0f)
    var scale = Vector3f(0f,0f,0f)
    var meshId = 0
    constructor (geometry: Geometry, material: IMaterial): this() {
        this.geometry = geometry
        this.material = material
    }


    fun translate(x: Float, y: Float, z: Float){
        position = Vector3f(x,y,z)
    }

    fun translate(vec: Vector3f){
        position = vec
    }

    fun scale(x: Float, y: Float, z: Float){
        scale = Vector3f(x,y,z)
    }

    fun scale(vec: Vector3f){
        position = vec
    }

    fun rotate(angle: Float, x: Float, y: Float, z: Float) {
        rotation = Vector4f(angle,x,y,z)
    }

    fun rotate(angle: Float, vec: Vector3f) {
        rotation = Vector4f(vec.x, vec.y, vec.z, angle)
    }

    fun updateMatrix(){
        val shaderProgram = MeshRenderer.shaderProgram
        transformationMatrix = Matrix4f().translate(position).rotate(rotation.w, rotation.x, rotation.y, rotation.z)
        shaderProgram.setUniformMatrix4f("u_model", transformationMatrix)
        shaderProgram.setUniformMatrix3f("u_normalMatrix", Matrix3f(Matrix3f(transformationMatrix).invert()).transpose())

    }

    override fun toString(): String {
        return "$meshId: $meshName"
    }

}