package ru.cool.lwjgl_kotlin.objects

import ru.cool.lwjgl_kotlin.geometry.AbstractGeometry
import ru.cool.lwjgl_kotlin.geometry.Geometry
import ru.cool.lwjgl_kotlin.material.IMaterial

class Mesh(
    override var name: String = "Mesh",
    override var geometry: Array<AbstractGeometry>?,
): SceneObject3D(name, geometry) {

    var meshId = 0

    constructor(name: String, geometry: Array<AbstractGeometry>, material: IMaterial): this(name, geometry) {
        this.geometry = geometry
        this.material = material
    }

    override fun toString(): String {
        return "$meshId: $name"
    }

}