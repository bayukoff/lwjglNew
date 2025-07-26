package ru.cool.lwjgl_kotlin.loaders.types

class Normal(override var x: Float, override var y: Float, override var z: Float): IVector3D {
    override fun toString(): String {
        return "Normal ($x, $y, $z)"
    }
}