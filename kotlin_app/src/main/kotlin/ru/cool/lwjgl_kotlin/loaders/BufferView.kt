package ru.cool.lwjgl_kotlin.loaders

data class BufferView(
    val buffer: Int,
    val byteOffset: Int,
    val byteLength: Int,
    val byteStride: Int,
    val target: Int
)
