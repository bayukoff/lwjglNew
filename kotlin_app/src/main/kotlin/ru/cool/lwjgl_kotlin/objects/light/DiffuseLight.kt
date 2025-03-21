package ru.cool.lwjgl_kotlin.objects.light

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.MeshRenderer

class DiffuseLight(var lightColor: Vector3f, var lightPosition: Vector3f): ILight {

    private val shaderProgram = MeshRenderer.shaderProgram

    fun translate(newPosition: Vector3f){
        shaderProgram.setUniformVector3f("u_lightPosition", newPosition)
    }

    override fun applyLight() {
        shaderProgram.setUniformVector3f("u_lightPosition", lightPosition)
        shaderProgram.setUniformVector3f("u_diffuseLightColor", lightColor)
    }
}