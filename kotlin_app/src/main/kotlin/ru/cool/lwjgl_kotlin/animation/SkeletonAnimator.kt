package ru.cool.lwjgl_kotlin.animation

import org.joml.Matrix4f
import ru.cool.lwjgl_kotlin.MeshRenderer
import ru.cool.lwjgl_kotlin.objects.Skeleton

class SkeletonAnimator(
    val skeleton: Skeleton,
    override val animations: MutableList<Animation>,
): Animator(animations) {

    private fun uploadBoneMatricesToShader(boneMatrices: MutableList<Matrix4f>) {
        val shaderProgram = MeshRenderer.shaderProgram
        shaderProgram.setUniformMatrix4fv("u_boneMatrices", boneMatrices)
    }

    override fun playAnimations(speed: Float){
        animations.forEach {
            it.play(speed)
            uploadBoneMatricesToShader(skeleton.getBoneMatrices())
        }
    }

    override fun stopAnimations(){
        animations.forEach {
            it.stop()
        }
    }
}