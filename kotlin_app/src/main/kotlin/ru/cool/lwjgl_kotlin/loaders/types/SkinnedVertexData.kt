package ru.cool.lwjgl_kotlin.loaders.types

import org.joml.Vector4f
import org.joml.Vector4i

data class SkinnedVertexData(override var vertex: Vertex, override var normal: Normal?, override var texCoords: UV?, var boneIndices: Vector4i, var weights: Vector4f): VertexData(vertex, normal, texCoords) {
}