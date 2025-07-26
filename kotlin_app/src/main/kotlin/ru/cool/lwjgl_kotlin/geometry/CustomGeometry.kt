package ru.cool.lwjgl_kotlin.geometry

import ru.cool.lwjgl_kotlin.loaders.types.Normal
import ru.cool.lwjgl_kotlin.loaders.types.UV
import ru.cool.lwjgl_kotlin.loaders.types.Vertex

class CustomGeometry(
    override val vertices: Array<Vertex>,
    override val normals: Array<Normal>?,
    override val textureCoords: Array<UV>?,
    override val indices: IntArray
):AbstractGeometry(vertices, normals, textureCoords, indices) {

    init{
        initBuffers()
    }
}