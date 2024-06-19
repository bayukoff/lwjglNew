package ru.cool.lwjgl.utils;

import org.lwjgl.opengl.GL30;

public class Debug {
    public static String glCheckError()
    {
        String errorType = "";
        int errorCode = GL30.glGetError();
        switch (errorCode) {
            case GL30.GL_INVALID_ENUM -> errorType = "INVALID_ENUM";
            case GL30.GL_INVALID_VALUE -> errorType = "INVALID_VALUE";
            case GL30.GL_INVALID_OPERATION -> errorType = "INVALID_OPERATION";
            case GL30.GL_STACK_OVERFLOW -> errorType = "STACK_OVERFLOW";
            case GL30.GL_STACK_UNDERFLOW -> errorType = "STACK_UNDERFLOW";
            case GL30.GL_OUT_OF_MEMORY -> errorType = "OUT_OF_MEMORY";
            case GL30.GL_INVALID_FRAMEBUFFER_OPERATION -> errorType = "INVALID_FRAMEBUFFER_OPERATION";
            default -> errorType = "OK";
        }
        return errorType;
    }
}
