package ru.cool.lwjgl_kotlin

import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.loaders.GLTFLoader
import org.lwjgl.opengl.GL30 as gl
import ru.cool.lwjgl_kotlin.loaders.ObjLoader
import ru.cool.lwjgl_kotlin.loaders.TextureLoader
import ru.cool.lwjgl_kotlin.material.StandardMaterial
import ru.cool.lwjgl_kotlin.material.TextureMaterial
import ru.cool.lwjgl_kotlin.objects.Mesh
import ru.cool.lwjgl_kotlin.objects.light.AmbientLight
import ru.cool.lwjgl_kotlin.objects.light.DiffuseLight
import ru.cool.lwjgl_kotlin.utils.Time

import kotlin.math.sin

class Game: IGame {

    private lateinit var ambientLight: AmbientLight
    private lateinit var diffuseLight: DiffuseLight
    private lateinit var m14: Mesh
    private lateinit var gauss: Mesh
    private lateinit var cubeObj: Mesh

    override fun create() {
        var cubeGLTF = GLTFLoader.loadModel("/models/gltf/testCube.glb")
        var textureGauss = TextureLoader.loadTexture("/textures/gauss.png")
        var textureM14 = TextureLoader.loadTexture("/textures/m14.png")
        m14 = Mesh(ObjLoader.loadModel("/models/m14.obj"),
            TextureMaterial(textureM14))
        gauss = Mesh(ObjLoader.loadModel("/models/gaussTriangle.obj"),
            TextureMaterial(textureGauss))
        cubeObj = Mesh(ObjLoader.loadModel("/models/untitled.obj"),
            StandardMaterial(Vector3f(0.5f,0.2f,0f)))
        ambientLight = AmbientLight(Vector3f(1f, 1f,1f), 0.6f)
        diffuseLight = DiffuseLight(Vector3f(1f,1f,1f), Vector3f(0f,5f,0f))

        MeshRenderer.drawMesh(m14)
        MeshRenderer.drawMesh(cubeObj)
        MeshRenderer.drawMesh(gauss)
    }

    override fun update() {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT or gl.GL_DEPTH_BUFFER_BIT)
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glEnable(gl.GL_DEPTH_TEST)
        MeshRenderer.beginDraw()
        ambientLight.applyLight()
        diffuseLight.applyLight()

        val lightPosition = Vector3f(0f, sin(Time.prevTime * 2).toFloat() * 10, -5f)
        diffuseLight.translate(lightPosition)
        m14.translate(-2f, -2f, -5f)
        m14.scale(0.5f, 0.5f, 0.5f)
        cubeObj.translate(10f,1f,0f)
        gauss.translate(0f,5f,-10f)
        gauss.scale(0.7f,0.7f,0.7f)
        gauss.rotate((Time.prevTime).toFloat(), 0f,1f,0f)

        MeshRenderer.draw()
    }
}