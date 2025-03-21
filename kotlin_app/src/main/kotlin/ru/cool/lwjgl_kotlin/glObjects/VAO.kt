package ru.cool.lwjgl_kotlin.glObjects

import org.lwjgl.opengl.GL33

class VAO {
    var vaoID = GL33.glGenVertexArrays()

    fun bind() = GL33.glBindVertexArray(vaoID)

    fun unbind() = GL33.glBindVertexArray(0)

    fun addVertexAttribute(index: Int, size: Int, stride: Int, pointer: Int)
        = GL33.glVertexAttribPointer(index, size, GL33.GL_FLOAT, false, stride, pointer.toLong())

    fun setVertexAttribute(index: Int, size: Int, stride: Int, pointer: Int){
        GL33.glEnableVertexAttribArray(index)
        addVertexAttribute(index, size, stride, pointer)
//        GL33.glDisableVertexAttribArray(index)
    }

    fun enableAttribute(attributeIndex: Int) = GL33.glEnableVertexAttribArray(attributeIndex)

    fun disableAttribute(attributeIndex: Int) = GL33.glDisableVertexAttribArray(attributeIndex)

    fun getAttribBufferBinding(index: Int) = GL33.glGetVertexAttribIi(index, GL33.GL_VERTEX_ARRAY_BUFFER_BINDING)

    fun getAttribEnabled(index: Int) = GL33.glGetVertexAttribIi(index, GL33.GL_VERTEX_ATTRIB_ARRAY_ENABLED)

    fun getAttribArraySize(index: Int) = GL33.glGetVertexAttribIi(index, GL33.GL_VERTEX_ATTRIB_ARRAY_SIZE)

    fun getAttribArrayStride(index: Int) = GL33.glGetVertexAttribIi(index, GL33.GL_VERTEX_ATTRIB_ARRAY_STRIDE)


}