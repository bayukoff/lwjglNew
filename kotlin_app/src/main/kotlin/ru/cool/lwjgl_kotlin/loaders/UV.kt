package ru.cool.lwjgl_kotlin.loaders

class UV(var u: Float, var v: Float): VertexData(u.toInt(), v.toInt()){

    override fun toString(): String {
        return "UV ($u, $v)"
    }

    override fun equals(other: Any?): Boolean {
        val otherUV = other as UV
        return u == otherUV.u && v == otherUV.v
    }
}