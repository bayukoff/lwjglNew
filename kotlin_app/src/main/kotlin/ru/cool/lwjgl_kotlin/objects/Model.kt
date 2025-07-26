package ru.cool.lwjgl_kotlin.objects

import ru.cool.lwjgl_kotlin.geometry.AbstractGeometry

class Model(
    override var name: String,
    override var geometry: Array<AbstractGeometry>?
): SceneObject3D(name, geometry) {

    var skeleton: Skeleton? = null

}