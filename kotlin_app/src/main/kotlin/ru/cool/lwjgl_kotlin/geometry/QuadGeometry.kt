package ru.cool.lwjgl_kotlin.geometry

import ru.cool.lwjgl_kotlin.loaders.types.Normal
import ru.cool.lwjgl_kotlin.loaders.types.UV
import ru.cool.lwjgl_kotlin.loaders.types.Vertex

class QuadGeometry: AbstractGeometry(
    arrayOf(
        Vertex(-1f, -1f, 0f),
        Vertex(-1f, 1f, 0f),
        Vertex(1f, 1f, 0f),
        Vertex(1f, -1f, 0f),
    ),
    arrayOf(
        Normal(0f, 0f, 1f),
        Normal(0f, 0f, 1f),
        Normal(0f, 0f, 1f),
        Normal(0f, 0f, 1f)
    ),
    arrayOf(
        UV(0f, 0f),
        UV(0f, 1f),
        UV(1f, 1f),
        UV(1f, 0f)
    ),
    intArrayOf(
        0, 1, 2,
        2, 0, 3
    )
){
    init {
        initBuffers()
    }
}