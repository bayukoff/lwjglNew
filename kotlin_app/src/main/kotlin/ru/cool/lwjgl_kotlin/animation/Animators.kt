package ru.cool.lwjgl_kotlin.animation

import ru.cool.lwjgl_kotlin.objects.Model

object Animators {
    private val animators = HashMap<Model, Animator>()

    fun add(model: Model, animator: Animator){
        animators[model] = animator
    }

    fun get(model: Model): Animator {
        return animators[model]!!
    }
}