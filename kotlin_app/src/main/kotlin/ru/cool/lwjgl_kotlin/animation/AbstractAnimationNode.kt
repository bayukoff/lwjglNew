package ru.cool.lwjgl_kotlin.animation

abstract class AbstractAnimationNode(
    open var nodeName: String,
    open var numPositionKeys: Int = 0,
    open var numScaleKeys: Int = 0,
    open var numRotationKeys: Int = 0,
    open var translateKeys: Array<VectorKey>?,
    open var scaleKeys: Array<VectorKey>?,
    open var rotationKeys: Array<QuaternionKey>?,
) {
}