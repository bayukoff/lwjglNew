package ru.cool.lwjgl_kotlin.objects.light

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.MeshRenderer
import ru.cool.lwjgl_kotlin.objects.SceneObject

class AmbientLight(override var lightColor: Vector3f, override var lightStrength: Float): Light("Ambient Light", lightColor, lightStrength) {

    override var name: String = "Ambient Light"

    override fun applyLight() {
        val shaderProgram = MeshRenderer.shaderProgram
        shaderProgram.setUniformFloat("u_lightStrength", lightStrength)
        shaderProgram.setUniformVector3f("u_ambientLightColor", lightColor)
    }

}