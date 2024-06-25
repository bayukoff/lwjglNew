package ru.cool.lwjgl.controls;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.cool.lwjgl.Config;
import ru.cool.lwjgl.input.Buttons;
import ru.cool.lwjgl.input.Keyboard;
import ru.cool.lwjgl.input.Mouse;
import ru.cool.lwjgl.objects.Camera;
import ru.cool.lwjgl.utils.Time;

public class CameraController extends MovingObjectsController{

    private Camera camera;

    public CameraController(Camera camera) {
        super(camera);
        this.camera = camera;
    }
    private void moveCamera(){
        float cameraSpeed = (float) (Time.deltaTime * camera.getSpeed());
        if (Keyboard.isButtonPress(Buttons.BACK)){
            camera.getPosition().add(new Vector3f(0,0, cameraSpeed).sub(camera.getDirection()).normalize().mul(cameraSpeed));
        }
        if (Keyboard.isButtonPress(Buttons.FORWARD)){
            camera.getPosition().add(new Vector3f(0,0, cameraSpeed).add(camera.getDirection()).normalize().mul(cameraSpeed));
        }
        if (Keyboard.isButtonPress(Buttons.RIGHT)){
            camera.getPosition().add(new Vector3f(camera.getDirection()).cross(camera.getUpVector()).normalize().mul(cameraSpeed));
        }
        if (Keyboard.isButtonPress(Buttons.LEFT)){
            camera.getPosition().sub(new Vector3f(camera.getDirection()).cross(camera.getUpVector()).normalize().mul(cameraSpeed));
        }
        if (Keyboard.isButtonPress(Buttons.UP)){
            camera.getPosition().add(new Vector3f(0, cameraSpeed,0));
        }
        if (Keyboard.isButtonPress(Buttons.DOWN)){
            camera.getPosition().sub(new Vector3f(0, cameraSpeed,0));
        }
    }

    private void rotateCamera(){
        float xOffset = (Mouse.getMousePosX() - Mouse.lastX);
        float yOffset = (Mouse.getMousePosY() - Mouse.lastY);
        Mouse.lastX = (int) Mouse.getMousePosX();
        Mouse.lastY = (int) Mouse.getMousePosY();

        xOffset *= Config.SENSITIVITY;
        yOffset *= Config.SENSITIVITY;

        camera.setRotationYaw(camera.getRotationYaw() + xOffset);
        camera.setRotationPitch((float) Math.clamp(camera.getRotationPitch() + yOffset, -89.9, 89.9));

        Vector3f direction = new Vector3f();
        direction.x = (float) ((float) Math.cos(Math.toRadians(camera.getRotationPitch())) * Math.cos(Math.toRadians(camera.getRotationYaw())));
        direction.y = -(float) Math.sin(Math.toRadians(camera.getRotationPitch()));
        direction.z = (float) ((float) Math.cos(Math.toRadians(camera.getRotationPitch())) * Math.sin(Math.toRadians(camera.getRotationYaw())));

        camera.setDirection(direction.normalize());
        camera.setViewMatrix(new Matrix4f().lookAt(camera.getPosition(), new Vector3f(camera.getPosition()).add(camera.getDirection()), camera.getUpVector()));
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void invoke() {
        this.moveCamera();
        this.rotateCamera();
    }

}


