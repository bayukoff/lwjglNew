package ru.cool.lwjgl.objects;

import org.joml.Vector3f;

public abstract class MovingObject {
    protected float speed;
    private Vector3f position;
    private Vector3f direction;
    public MovingObject(Vector3f position){
        this.position = position;
        this.direction = new Vector3f(0f, 0f, -1f);
    }
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public Vector3f getDirection() {
        return direction;
    }
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
