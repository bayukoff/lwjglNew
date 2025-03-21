package ru.cool.lwjgl_kotlin.texture

import org.lwjgl.opengl.GL30 as gl

class Texture {
    var textureId = 0
    var textureName = ""
    var textureWidth = 0
    var textureHeight = 0

    fun setTextureParameter(pname: Int, param: Int){
        gl.glTexParameteri(gl.GL_TEXTURE_2D, pname, param)
    }

    fun bindTexture(){
        gl.glBindTexture(gl.GL_TEXTURE_2D, textureId)
    }

    fun unbindTexture(){
        gl.glBindTexture(gl.GL_TEXTURE_2D, textureId)
    }

}