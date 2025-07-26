package ru.cool.lwjgl_kotlin.animation

import ru.cool.lwjgl_kotlin.objects.TransformableObject

class AnimationNode(
    override var nodeName: String,
    override var numPositionKeys: Int,
    override var numScaleKeys: Int,
    override var numRotationKeys: Int,
    override var translateKeys: Array<VectorKey>?,
    override var scaleKeys: Array<VectorKey>?,
    override var rotationKeys: Array<QuaternionKey>?,
    var animationObject: TransformableObject?
): AbstractAnimationNode(nodeName, numPositionKeys, numScaleKeys, numRotationKeys, translateKeys, scaleKeys, rotationKeys)
{
    override fun toString(): String {
        return "$nodeName: position keys: $numPositionKeys, scale keys: $numScaleKeys, rotation keys: $numRotationKeys"
    }
}