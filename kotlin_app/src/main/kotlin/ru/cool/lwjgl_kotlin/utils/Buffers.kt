package ru.cool.lwjgl_kotlin.utils

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
        // Проверяем тип буфера и выводим содержимое в зависимости от типа
        when (buffer) {
            is ByteBuffer -> {
                println("ByteBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим байты
                }
            }
            is IntBuffer -> {
                println("IntBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим целые числа
                }
            }
            is FloatBuffer -> {
                println("FloatBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим числа с плавающей точкой
                }
            }
            is ShortBuffer -> {
                println("ShortBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим короткие числа
                }
            }
            is LongBuffer -> {
                println("LongBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим длинные числа
                }
            }
            is DoubleBuffer -> {
                println("DoubleBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим числа с двойной точностью
                }
            }
            is CharBuffer -> {
                println("CharBuffer contents:")
                while (buffer.hasRemaining()) {
                    print("${buffer.get()} ") // Выводим символы
                }
            }
            else -> {
                println("Unknown buffer type")
            }
        }
        // Восстанавливаем начальную позицию буфера
        buffer.flip()
    }
}