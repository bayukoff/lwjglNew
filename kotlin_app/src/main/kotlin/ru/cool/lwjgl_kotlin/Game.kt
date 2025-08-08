package ru.cool.lwjgl_kotlin

import org.joml.Matrix4f
import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.animation.Animators
import ru.cool.lwjgl_kotlin.loaders.ModelLoader
import ru.cool.lwjgl_kotlin.objects.Bone
import ru.cool.lwjgl_kotlin.objects.Mesh
import ru.cool.lwjgl_kotlin.objects.Model
import ru.cool.lwjgl_kotlin.objects.light.AmbientLight
import ru.cool.lwjgl_kotlin.objects.light.DiffuseLight
import ru.cool.lwjgl_kotlin.utils.Time
import kotlin.math.sin
import org.lwjgl.opengl.GL30 as gl

class Game: IGame {

    private lateinit var ambientLight: AmbientLight
    private lateinit var diffuseLight: DiffuseLight
    private lateinit var test: Model
    private lateinit var person: Model

    override fun create() {
        ambientLight = AmbientLight(Vector3f(1f, 1f,1f), 0.6f)
        diffuseLight = DiffuseLight(Vector3f(1f,1f,1f), Vector3f(0f,5f,0f), 1f)
        test = ModelLoader.loadModel("C:\\Users\\PrudensCool\\Desktop\\models\\kubiki.glb")
        person = ModelLoader.loadModel("C:\\Users\\PrudensCool\\Desktop\\models\\animated-person.glb")
        MeshRenderer.addObjectToDraw(test)
        MeshRenderer.addObjectToDraw(person)
    }


    override fun update() {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT or gl.GL_DEPTH_BUFFER_BIT)
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glEnable(gl.GL_DEPTH_TEST)

        MeshRenderer.beginDraw()
        Animators.get(test).playAnimations(0.2f)
        Animators.get(person).playAnimations(0.002f)
        println(Animators.get(test).animations)

        ambientLight.applyLight()
        diffuseLight.applyLight()
        test.translate(0f,0f,0f)
        test.scale(1f,1f,1f)
        val lightPosition = Vector3f(0f, sin(Time.prevTime * 2).toFloat() * 10, -5f)
        diffuseLight.translate(lightPosition)

        MeshRenderer.draw()
    }
}