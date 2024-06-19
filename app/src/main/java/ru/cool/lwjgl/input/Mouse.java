package ru.cool.lwjgl.input;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import ru.cool.lwjgl.Config;

import java.nio.DoubleBuffer;

public class Mouse {
    private static final DoubleBuffer mousePosX = BufferUtils.createDoubleBuffer(1);
    private static final DoubleBuffer mousePosY = BufferUtils.createDoubleBuffer(1);
    public static int lastX = Config.WINDOW_WIDTH / 2;
    public static int lastY = Config.WINDOW_HEIGHT / 2;
    private static long currentWindow;
    private static boolean leftButtonPress = false;
    private static boolean leftButtonRelease = true;
    private static boolean rightButtonPress = false;
    private static boolean rightButtonRelease = true;

    public static boolean isLeftButtonPress(){
        leftButtonPress = GLFW.glfwGetMouseButton(currentWindow, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        if (leftButtonPress)
            leftButtonRelease = false;
        return leftButtonPress;
    }
    public static boolean isLeftButtonRelease(){
        if (!leftButtonPress && !leftButtonRelease) {
            leftButtonRelease = true;
            return true;
        }
        return false;
    }
    public static boolean isRightButtonPress(){
        rightButtonPress = GLFW.glfwGetMouseButton(currentWindow, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        if (rightButtonPress)
            rightButtonRelease = false;
        return rightButtonPress;
    }
    public static boolean isRightButtonRelease(){
        if (!rightButtonPress && !rightButtonRelease) {
            rightButtonRelease = true;
            return true;
        }
        return false;
    }
    public static void handleInput(long window){
        currentWindow = window;
    }

    public static float getMousePosX() {
        GLFW.glfwGetCursorPos(currentWindow, mousePosX, null);
        double posX = mousePosX.get();
        mousePosX.clear();
        return (float) posX;
    }

    public static float getMousePosY() {
        GLFW.glfwGetCursorPos(currentWindow, null, mousePosY);
        double posY = mousePosY.get();
        mousePosY.clear();
        return (float) posY;
    }

}
