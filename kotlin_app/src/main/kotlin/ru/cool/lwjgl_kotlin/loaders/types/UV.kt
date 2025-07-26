package ru.cool.lwjgl_kotlin.loaders.types

class UV(var u: Float, var v: Float): IVector2D{

    override val x: Float
        get() = u
    override val y: Float
        get() = v

    override fun toString(): String {
        return "UV ($u, $v)"
    }
}