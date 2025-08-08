package ru.cool.lwjgl_kotlin.animation

import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import ru.cool.lwjgl_kotlin.objects.TransformableObject

class Animation(
    var name: String,
    var animationNodes: Array<AnimationNode> = emptyArray(),
    var duration: Double = 0.0  //animation duration in ticks (by default 1s/25ticks)
) {

    private var animationStartTime = 0.0
    var animationTime = 0.0
    private var animationStarted = false
    private var animationEnded = false
    
    fun play(animationSpeed: Float = 1f){
        if (!animationStarted){
            animationStartTime = GLFW.glfwGetTime()
            animationStarted = true
            animationEnded = false
        }
        for (animationNode in animationNodes){
            val animatingObject = animationNode.animationObject
            if (animatingObject != null){
                animationTime += (GLFW.glfwGetTime() - animationStartTime) * animationSpeed
                val animTimeInTicks = animationTime * 25
                if (animTimeInTicks > duration){
                    animationEnded = true
                    animationStarted = false
                    animationTime = 0.0
                }
                if (!animationNode.scaleKeys.isNullOrEmpty()) {
                    animScale(animationNode.scaleKeys, animTimeInTicks, animatingObject)
                }
                if (!animationNode.rotationKeys.isNullOrEmpty()) {
                    animQuaternionTransform(animationNode.rotationKeys, animTimeInTicks, animatingObject)
                }
                if (!animationNode.translateKeys.isNullOrEmpty()) {
                    animTranslate(animationNode.translateKeys, animTimeInTicks, animatingObject)
                }
            }
        }
    }

    fun stop(){
        if (animationStarted){
            animationStarted = false
            animationEnded = true
            animationStartTime = 0.0
            animationTime = 0.0
            animationNodes.forEach {
                val animObject = it.animationObject
                if (animObject != null){
                    resetTransformations(animObject)
                }
            }
        }
    }

    private fun resetTransformations(transformable: TransformableObject){
        val transformableStartMatrix = transformable.startMatrix ?: Matrix4f()
        val vectorTransform = Vector3f()
        transformable.translate(transformableStartMatrix.getTranslation(vectorTransform))
        transformable.scale(transformableStartMatrix.getScale(vectorTransform))
        transformable.rotate(transformableStartMatrix.getNormalizedRotation(Quaternionf()))
    }

    private fun animTranslate(keys: Array<VectorKey>?, animTime: Double, animatingObject: TransformableObject){
        animVectorTransform(keys, animTime, animatingObject, false)
    }

    private fun animScale(keys: Array<VectorKey>?, animTime: Double, animatingObject: TransformableObject){
        animVectorTransform(keys, animTime, animatingObject, true)
    }

    /**
     * Применяется для аимации векторных трансформаций.
     */
    private fun animVectorTransform(keys: Array<VectorKey>?, animTime: Double, animatingObject: TransformableObject, scale: Boolean){
        if (!keys.isNullOrEmpty()){
            val keysId = findNeighbourKeysTime(animTime, keys)
            val prevKeyId = keysId.first
            val nextKeyId = keysId.second
            val prevKeyValue = keys[prevKeyId].value
            val nextKeyValue = keys[nextKeyId].value
            val prevKeyTime = keys[prevKeyId].time
            val nextKeyTime = keys[nextKeyId].time
            val alpha = (animTime - prevKeyTime) / (nextKeyTime - prevKeyTime)
            val prevKeyVector = Vector3f(prevKeyValue.x(), prevKeyValue.y(), prevKeyValue.z())
            val nextKeyVector = Vector3f(nextKeyValue.x(), nextKeyValue.y(), nextKeyValue.z())
            val finalVector = Vector3f(prevKeyVector).lerp(nextKeyVector, alpha.toFloat())
            if (scale)
                animatingObject.scale(finalVector)
            else
                animatingObject.translate(finalVector)
        }
    }

    /**
     * Применяется для анимации кватернионных трансформаций
     */
    private fun animQuaternionTransform(keys: Array<QuaternionKey>?, animTime: Double, animatingObject: TransformableObject){
        if (!keys.isNullOrEmpty()){
            val keysId = findNeighbourKeysTime(animTime, keys)
            val prevKeyId = keysId.first
            val nextKeyId = keysId.second
            val prevKeyValue = keys[prevKeyId].value
            val nextKeyValue = keys[nextKeyId].value
            val prevKeyTime = keys[prevKeyId].time
            val nextKeyTime = keys[nextKeyId].time
            val alpha = (animTime - prevKeyTime) / (nextKeyTime - prevKeyTime)
            val prevKeyQuaternion = Quaternionf(prevKeyValue.x(), prevKeyValue.y(), prevKeyValue.z(), prevKeyValue.w())
            val nextKeyQuaternion = Quaternionf(nextKeyValue.x(), nextKeyValue.y(), nextKeyValue.z(), nextKeyValue.w())
            val finalQuaternion = Quaternionf(prevKeyQuaternion).slerp(nextKeyQuaternion, alpha.toFloat())
            animatingObject.rotate(finalQuaternion)
        }
    }

    private fun <T : AnimKey> findNeighbourKeysTime(currentAnimTimeInTicks: Double, keys: Array<T>): Pair<Int, Int>{
        for (keyId in 0 until keys.size - 1){
            val prevKeyTime = keys[keyId].time
            val nextKeyTime = keys[keyId + 1].time
            if (currentAnimTimeInTicks >= prevKeyTime && currentAnimTimeInTicks < nextKeyTime)
                return keyId to keyId + 1
        }
        return Pair(keys.size - 2, keys.size - 1)
    }

    override fun toString(): String {
        return "Animation: $name \n\tAmount animation nodes: ${animationNodes.size} \n\tAnimation Duration: $duration\n"
    }
}