package ru.cool.lwjgl_kotlin.animation

class SceneObjectAnimator(
    override val animations: MutableList<Animation>
): Animator(
    animations
) {
    override fun playAnimations(speed: Float) {

    }

    override fun stopAnimations() {

    }
}