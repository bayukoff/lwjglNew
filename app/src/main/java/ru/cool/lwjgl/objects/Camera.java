package ru.cool.lwjgl.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.cool.lwjgl.Config;

public class Camera extends MovingObject{
    private final Vector3f upVector;
    private float rotationPitch = 0;
    private float rotationYaw = -90;
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    public Camera(Vector3f position) {
        super(position);
        this.upVector = new Vector3f(0f,1f,0f);
        this.viewMatrix = new Matrix4f().lookAt(this.getPosition(), new Vector3f(this.getPosition()).add(this.getDirection()), this.getUpVector());
        this.projectionMatrix = new Matrix4f().perspective(45, (float) Config.WINDOW_WIDTH / Config.WINDOW_HEIGHT, 0.01f, 100f);
    }
    public Camera(Vector3f position, float cameraSpeed){
        this(position);
        this.speed = cameraSpeed;
    }

    public Vector3f getUpVector() {
        return upVector;
    }
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }
    public float getRotationPitch() {
        return rotationPitch;
    }
    public void setRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }
    public float getRotationYaw() {
        return rotationYaw;
    }
    public void setRotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
    }
}
