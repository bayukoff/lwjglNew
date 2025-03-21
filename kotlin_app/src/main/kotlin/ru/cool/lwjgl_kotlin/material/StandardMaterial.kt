package ru.cool.lwjgl_kotlin.material

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.MeshRenderer

class StandardMaterial(var color: Vector3f): IMaterial {

    override fun applyMaterial() {
        val shaderProgram = MeshRenderer.shaderProgram
        shaderProgram.setUniformVector3f("color", color)
        shaderProgram.setUniformBoolean("u_hasColor", true)
        shaderProgram.setUniformBoolean("u_hasTexture", false)
    }
}