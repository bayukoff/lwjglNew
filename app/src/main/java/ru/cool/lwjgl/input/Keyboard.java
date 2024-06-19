package ru.cool.lwjgl.input;

import org.lwjgl.glfw.GLFW;

public class Keyboard {
    public static long currentWindow;

    public static boolean isButtonPress(Buttons pressedKey){
        return GLFW.glfwGetKey(currentWindow, pressedKey.getGlfwKey()) == GLFW.GLFW_PRESS;
    }

    public static boolean isButtonRelease(Buttons releaseKey){
        return GLFW.glfwGetKey(currentWindow, releaseKey.getGlfwKey()) == GLFW.GLFW_RELEASE;
    }

    public static void handleInput(long window){
        currentWindow = window;
    }
}
