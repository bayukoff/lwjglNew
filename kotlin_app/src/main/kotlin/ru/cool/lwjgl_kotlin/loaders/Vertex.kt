package ru.cool.lwjgl_kotlin.loaders

class Vertex(var x: Float, var y: Float, var z: Float): Comparable<Vertex>, VertexData(x.toInt(),y.toInt()) {
    override fun compareTo(other: Vertex): Int {
        if(this.x == other.x && this.y == other.y && this.z == other.z)
            return 0
        if (this.x > other.x && this.y > other.y && this.z > other.z)
            return 1
        return -1
    }

    override fun toString(): String {
        return "Vertex ($x, $y, $z)"
    }

    override fun equals(other: Any?): Boolean {
        val otherVertex = other as Vertex
        return x == otherVertex.x && y == otherVertex.y && z == otherVertex.z
    }
}