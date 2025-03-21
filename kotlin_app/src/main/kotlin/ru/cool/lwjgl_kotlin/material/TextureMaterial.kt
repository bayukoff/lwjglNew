package ru.cool.lwjgl_kotlin.material

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.MeshRenderer
import ru.cool.lwjgl_kotlin.texture.Texture

class TextureMaterial(var texture: Texture, var color: Vector3f?): IMaterial {
    constructor(texture: Texture): this(texture, null)

    override fun applyMaterial() {
        val shaderProgram = MeshRenderer.shaderProgram
        if (color == null)
            shaderProgram.setUniformBoolean("u_hasColor", false)
        else
            shaderProgram.setUniformVector3f("color", color!!)
        shaderProgram.setUniformBoolean("u_hasTexture", true)
    }
}