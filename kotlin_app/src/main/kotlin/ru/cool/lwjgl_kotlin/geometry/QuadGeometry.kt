package ru.cool.lwjgl_kotlin.geometry

import org.lwjgl.BufferUtils

class QuadGeometry: Geometry(
    BufferUtils.createFloatBuffer(32).apply {
        put(
            floatArrayOf(
                -1f, -1f, 0f, 0f, 0f, 1f,  0f, 0f,
                -1f, 1f, 0f,  0f, 0f, 1f,  0f, 1f,
                1f, 1f, 0f,   0f, 0f, 1f,  1f, 1f,
                1f, -1f, 0f,  0f, 0f, 1f,  1f, 0f
            )
        )
        flip()
    },
    BufferUtils.createIntBuffer(6).apply {
        put(
            intArrayOf(
                0, 1, 2,
                2, 0, 3
            )
        )
        flip()
    },
    true,
    true
)