package ru.cool.lwjgl_kotlin.loaders

class Normal(var x: Float, var y: Float, var z: Float): VertexData(x.toInt(),y.toInt()) {
    override fun toString(): String {
        return "Normal ($x, $y, $z)"
    }

    override fun equals(other: Any?): Boolean {
        val otherVertex = other as Normal
        return x == otherVertex.x && y == otherVertex.y && z == otherVertex.z
    }
}