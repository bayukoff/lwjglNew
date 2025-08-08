package ru.cool.lwjgl_kotlin.animation

class SceneObjectAnimator(
    override val animations: MutableList<Animation>
): Animator(
    animations
) {
    override fun playAnimations(speed: Float) {
        animations.forEach { it.play(speed) }
    }

    override fun stopAnimations() {
        animations.forEach {
            it.stop()
        }
    }
}