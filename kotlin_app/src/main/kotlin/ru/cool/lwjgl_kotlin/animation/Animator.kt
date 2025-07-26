package ru.cool.lwjgl_kotlin.animation

abstract class Animator(
    open val animations: MutableList<Animation>
) {

    abstract fun playAnimations(speed: Float = 1f)
    abstract fun stopAnimations()

}