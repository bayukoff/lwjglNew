package ru.cool.lwjgl_kotlin.objects.light

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.MeshRenderer

class AmbientLight(var lightColor: Vector3f, var lightStrength: Float): ILight {
    override fun applyLight() {
        val shaderProgram = MeshRenderer.shaderProgram
        shaderProgram.setUniformFloat("u_lightStrength", lightStrength)
        shaderProgram.setUniformVector3f("u_ambientLightColor", lightColor)
    }

}