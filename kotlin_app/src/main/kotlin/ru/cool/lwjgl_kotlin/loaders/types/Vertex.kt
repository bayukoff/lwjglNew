package ru.cool.lwjgl_kotlin.loaders.types

class Vertex(override var x: Float, override var y: Float, override var z: Float): Comparable<Vertex>, IVector3D {
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
}