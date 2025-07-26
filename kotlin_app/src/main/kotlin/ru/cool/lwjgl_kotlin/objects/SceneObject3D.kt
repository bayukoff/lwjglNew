package ru.cool.lwjgl_kotlin.objects

import ru.cool.lwjgl_kotlin.geometry.AbstractGeometry
import ru.cool.lwjgl_kotlin.geometry.Geometry

abstract class SceneObject3D(
    override var name: String,
    open var geometry: Array<AbstractGeometry>?,
): SceneObject(name)