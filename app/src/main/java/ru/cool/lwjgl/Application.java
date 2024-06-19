package ru.cool.lwjgl;

import ru.cool.lwjgl.window.Window;

import static ru.cool.lwjgl.Config.WINDOW_HEIGHT;
import static ru.cool.lwjgl.Config.WINDOW_WIDTH;

public class Application {


    public static void main(String[] args) {

        Window mainWindow = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "Engine").createWindow();
        mainWindow.updateWindow();
    }
}
