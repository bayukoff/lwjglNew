package ru.cool.lwjgl_kotlin

import ru.cool.lwjgl_kotlin.loaders.GLTFLoader

fun main(){
//    val window = Window(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, "Engine")
//    window.createWindow()
//    window.update()
    GLTFLoader.loadModel("/models/gltf/testCube.glb")

}