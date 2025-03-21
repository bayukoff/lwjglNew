package ru.cool.lwjgl_kotlin.loaders

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import ru.cool.lwjgl_kotlin.texture.Texture
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.nio.ByteBuffer

import org.lwjgl.opengl.GL33 as gl

object TextureLoader {
    private lateinit var imageData: ByteBuffer
    private lateinit var textureName: String
    private var width = 0
    private var height = 0
    private var channels = 0

    fun loadTexture(path: String): Texture {
        var resource = javaClass.getResourceAsStream(path)!!
        val byteBuffer = ByteBuffer.allocateDirect(resource.available())
        byteBuffer.put(resource.readBytes())
        byteBuffer.flip()

        MemoryStack.stackPush().use { stack ->
            val imageWidth = stack.mallocInt(1)
            val imageHeight = stack.mallocInt(1)
            val imageChannels = stack.mallocInt(1)

            STBImage.stbi_info_from_memory(byteBuffer, imageWidth, imageHeight, imageChannels)
            width = imageWidth.get(0)
            height = imageHeight.get(0)
            channels = imageChannels.get(0)
            STBImage.stbi_set_flip_vertically_on_load(true)
            imageData = STBImage.stbi_load_from_memory(byteBuffer, imageWidth, imageHeight, imageChannels, 0)!!
        }

        return createTexture()
    }

    private fun createTexture(): Texture {
        val texture = Texture()
        texture.textureId = gl.glGenTextures()

        texture.textureWidth = width
        texture.textureHeight = height
        texture.bindTexture()
        val colors = if(channels == 4) gl.GL_RGBA else gl.GL_RGB
        gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, colors, width, height, 0, colors, gl.GL_UNSIGNED_BYTE, this.imageData)
        gl.glGenerateMipmap(gl.GL_TEXTURE_2D);
        texture.setTextureParameter(gl.GL_TEXTURE_WRAP_S, gl.GL_MIRRORED_REPEAT);
        texture.setTextureParameter(gl.GL_TEXTURE_WRAP_T, gl.GL_MIRRORED_REPEAT);
        texture.setTextureParameter(gl.GL_TEXTURE_MIN_FILTER, gl.GL_NEAREST_MIPMAP_NEAREST);
        texture.setTextureParameter(gl.GL_TEXTURE_MAG_FILTER, gl.GL_NEAREST);
        texture.unbindTexture()
        return texture
    }
}