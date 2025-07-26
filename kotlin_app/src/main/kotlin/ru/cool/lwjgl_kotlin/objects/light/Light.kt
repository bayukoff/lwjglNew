package ru.cool.lwjgl_kotlin.objects.light

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.objects.SceneObject3D

abstract class Light(override var name: String, open var lightColor: Vector3f, open var lightStrength: Float): SceneObject3D(name, null), ILight {
}