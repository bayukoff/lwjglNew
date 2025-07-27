package ru.cool.lwjgl_kotlin.objects

import org.joml.Matrix3f
import org.joml.Matrix4f
import ru.cool.lwjgl_kotlin.MeshRenderer

class Bone(var name: String): TransformableObject() {
    var boneId: Int = 0
    var parentBone: Bone? = null
    var children: MutableList<Bone> = mutableListOf()
    var verticesId: Array<Int> = emptyArray()
    var weights: Array<Float> = emptyArray()
    var offsetMatrix: Matrix4f? = null

    fun getGlobalMatrix(): Matrix4f {
        val localMatrix = Matrix4f()
        startMatrix?.let { localMatrix.set(it) }

        localMatrix.translate(position)
            .rotate(rotation)
            .scale(scale)

        return parentBone?.getGlobalMatrix()?.mul(localMatrix) ?: localMatrix
    }

//    fun getGlobalMatrix(): Matrix4f {
//        val localMatrix = Matrix4f()
//        if (startMatrix != null)
//            localMatrix.mul(startMatrix)
//        localMatrix
//            .translate(position)
//            .rotate(rotation)
//            .scale(scale)
//        return parentBone?.getGlobalMatrix()?.mul(localMatrix) ?: localMatrix
//    }

    fun calculateFinalMatrix(): Matrix4f {
        return Matrix4f(getGlobalMatrix()).mul(offsetMatrix)
    }

    override fun updateMatrix(){
        val shaderProgram = MeshRenderer.shaderProgram
    }


    override fun toString(): String {
        return "Bone(name='$name', parentBone=$parentBone)"
    }
}