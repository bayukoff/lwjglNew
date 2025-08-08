package ru.cool.lwjgl_kotlin.input.controllers

import ru.cool.lwjgl_kotlin.input.Buttons
import ru.cool.lwjgl_kotlin.input.Keyboard
import ru.cool.lwjgl_kotlin.input.Mouse
import ru.cool.lwjgl_kotlin.objects.camera.PerspectiveCamera
import ru.cool.lwjgl_kotlin.utils.Time
import ru.cool.lwjgl_kotlin.Config
import kotlin.math.cos
import kotlin.math.sin
import org.joml.Math
import org.joml.Vector3f
import ru.cool.lwjgl_kotlin.*

class CameraController(var camera: PerspectiveCamera, var speed: Float): IController {
    override fun invoke() {
        moveCamera()
        rotateCamera()
    }

    fun rotateCamera(){
        val mouseX = (Mouse.getMousePosX() - Mouse.lastX) * Config.SENSITIVITY
        val mouseY = (Mouse.getMousePosY() - Mouse.lastY) * Config.SENSITIVITY

        Mouse.lastX = Mouse.getMousePosX().toInt()
        Mouse.lastY = Mouse.getMousePosY().toInt()

        camera.cameraRotationYaw += mouseX
        camera.cameraRotationPitch += mouseY

        camera.cameraRotationPitch = Math.clamp(-89f, 89f, camera.cameraRotationPitch)

        val directionX = (Math.cos(Math.toRadians(camera.cameraRotationPitch)) * Math.cos(Math.toRadians(camera.cameraRotationYaw)));
        val directionY = -Math.sin(Math.toRadians(camera.cameraRotationPitch));
        val directionZ = (Math.cos(Math.toRadians(camera.cameraRotationPitch)) * Math.sin(Math.toRadians(camera.cameraRotationYaw)));

        val direction = Vector3f(directionX, directionY, directionZ)
        camera.direction = direction
        camera.updateMatrix()
    }

    fun moveCamera() {
        val cameraSpeed = speed * Time.deltaTime.toFloat()
        if (Keyboard.isButtonPress(Buttons.W)) {
            camera.move(Vector3f(0f, 0f, cameraSpeed).sub(camera.direction).normalize().mul(cameraSpeed))
        }
        if (Keyboard.isButtonPress(Buttons.S)) {
            camera.move(Vector3f(0f, 0f, cameraSpeed).add(camera.direction).normalize().mul(cameraSpeed))
        }
        if (Keyboard.isButtonPress(Buttons.A)){
            camera.move(Vector3f(camera.direction).cross(camera.upVector) * cameraSpeed)
        }
        if (Keyboard.isButtonPress(Buttons.D)){
            camera.move(Vector3f(camera.direction).cross(camera.upVector) * -cameraSpeed)
        }
        if (Keyboard.isButtonPress(Buttons.LEFT_CTR)){
            camera.move(0f,cameraSpeed,0f)
        }
        if (Keyboard.isButtonPress(Buttons.SPACE)){
            camera.move(0f,-cameraSpeed,0f)
        }
        if (Keyboard.isButtonPress(Buttons.R)){
            camera.setPos(Vector3f(0f,0f,3f))
        }
    }
}