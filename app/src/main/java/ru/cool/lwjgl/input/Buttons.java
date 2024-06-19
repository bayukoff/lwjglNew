package ru.cool.lwjgl.input;

import org.lwjgl.glfw.GLFW;

public enum Buttons {
    RIGHT(GLFW.GLFW_KEY_D),
    LEFT(GLFW.GLFW_KEY_A),
    FORWARD(GLFW.GLFW_KEY_W),
    BACK(GLFW.GLFW_KEY_S),
    UP(GLFW.GLFW_KEY_SPACE),
    DOWN(GLFW.GLFW_KEY_LEFT_CONTROL),
    ESCAPE(GLFW.GLFW_KEY_ESCAPE);

    private final int glfwKey;
    Buttons(int glfwKey){
        this.glfwKey = glfwKey;
    }

    public int getGlfwKey(){
        return glfwKey;
    }
}
