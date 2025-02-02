package ru.cool.lwjgl_kotlin.glObjects

import org.lwjgl.opengl.GL30

class VAO {
    var vaoID = GL30.glGenVertexArrays()

    fun bindVAO() = GL30.glBindVertexArray(vaoID)

    fun unbindVAO() = GL30.glBindVertexArray(0)

    fun addVertexAttribute(index: Int, size: Int, stride: Int, pointer: Int)
        = GL30.glVertexAttribPointer(index, size, GL30.GL_FLOAT, false, stride, pointer.toLong())

    fun setVertexAttribute(index: Int, size: Int, stride: Int, pointer: Int){
        GL30.glEnableVertexAttribArray(index)
        addVertexAttribute(index, size, stride, pointer)
        GL30.glDisableVertexAttribArray(index)
    }

    fun enableAttribute(attributeIndex: Int) = GL30.glEnableVertexAttribArray(attributeIndex)

    fun disableAttribute(attributeIndex: Int) = GL30.glDisableVertexAttribArray(attributeIndex)

    fun getAttribBufferBinding(index: Int) = GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ARRAY_BUFFER_BINDING)

    fun getAttribEnabled(index: Int) = GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ATTRIB_ARRAY_ENABLED)

    fun getAttribArraySize(index: Int) = GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ATTRIB_ARRAY_SIZE)

    fun getAttribArrayStride(index: Int) = GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ATTRIB_ARRAY_STRIDE)


}