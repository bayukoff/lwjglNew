package ru.cool.lwjgl_kotlin

fun main(){
    val window = Window(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, "Engine")
    window.createWindow()
    window.update()

}