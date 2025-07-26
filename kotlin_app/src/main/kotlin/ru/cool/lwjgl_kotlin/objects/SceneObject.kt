package ru.cool.lwjgl_kotlin.objects

import org.joml.Matrix3f
import org.joml.Matrix4f
import ru.cool.lwjgl_kotlin.MeshRenderer
import ru.cool.lwjgl_kotlin.material.IMaterial

abstract class SceneObject(
    open var name: String,
): TransformableObject() {
    var children: Array<SceneObject>? = emptyArray()
    var parent: SceneObject? = null
    var material: IMaterial? = null

    companion object{
        var depthCounter = 0
    }

    fun getGlobalMatrix(): Matrix4f {
        val localMatrix = Matrix4f()
        if (startMatrix != null)
            localMatrix.mul(startMatrix)
        localMatrix
            .translate(position)
            .rotate(rotation)
            .scale(scale)
        return parent?.getGlobalMatrix()?.mul(localMatrix) ?: localMatrix
    }

    override fun updateMatrix(){
        val shaderProgram = MeshRenderer.shaderProgram
        shaderProgram.setUniformMatrix4f("u_model", getGlobalMatrix())
        shaderProgram.setUniformMatrix3f("u_normalMatrix", Matrix3f(Matrix3f(getGlobalMatrix()).invert()).transpose())
    }

    fun findObjectByName(name: String): SceneObject?{
        if (name == this.name){
            return this
        }
        for (child in children!!){
            if (child.name == name){
                return child
            }
            findObjectByName(child.name)
        }
        return null
    }

    override fun toString(): String {
        return "$name, ${children.contentToString()}"
    }

    fun traverse(){
        val indent = "  ".repeat(depthCounter++)
        println("$indent $name")
        if (children != null)
            children?.forEach {
                it.traverse()
            }
        depthCounter--
    }

}