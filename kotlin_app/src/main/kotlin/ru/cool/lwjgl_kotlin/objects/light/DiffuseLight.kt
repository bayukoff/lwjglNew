package ru.cool.lwjgl_kotlin.objects.light

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.MeshRenderer

class DiffuseLight(override var lightColor: Vector3f, var lightPosition: Vector3f, override var lightStrength: Float): Light("Diffuse Light", lightColor, lightStrength) {

    override var name: String = "Ambient Light"
    private val shaderProgram = MeshRenderer.shaderProgram

     override fun translate(vec: Vector3f){
        shaderProgram.setUniformVector3f("u_lightPosition", vec)
    }

    override fun applyLight() {
        shaderProgram.setUniformVector3f("u_lightPosition", lightPosition)
        shaderProgram.setUniformVector3f("u_diffuseLightColor", lightColor)
    }
}