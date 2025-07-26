package ru.cool.lwjgl_kotlin.utils

import org.lwjgl.BufferUtils
import org.lwjgl.assimp.AIVector3D
import org.lwjgl.system.MemoryUtil
import ru.cool.lwjgl_kotlin.loaders.types.IVector
import ru.cool.lwjgl_kotlin.loaders.types.IVector2D
import ru.cool.lwjgl_kotlin.loaders.types.IVector3D
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.DoubleBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.LongBuffer
import java.nio.ShortBuffer

object Buffers {
    fun showBufferData(buffer: Buffer) {
        when (buffer) {
            is ByteBuffer -> {
                println("ByteBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            is IntBuffer -> {
                println("IntBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            is FloatBuffer -> {
                println("FloatBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            is ShortBuffer -> {
                println("ShortBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            is LongBuffer -> {
                println("LongBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            is DoubleBuffer -> {
                println("DoubleBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            is CharBuffer -> {
                println("CharBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ")
                }
            }
            else -> {
                println("Unknown buffer type")
            }
        }
        buffer.flip()
    }



    inline fun <T: Buffer> T.memUse(block: (T) -> Unit){
        try{
            block(this)
        }finally {
            MemoryUtil.memFree(this)
        }
    }

}