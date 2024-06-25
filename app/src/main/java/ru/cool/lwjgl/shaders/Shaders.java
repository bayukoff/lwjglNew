package ru.cool.lwjgl.shaders;

public class Shaders {

    private static ShaderProgram program;

    public static void setActiveShaderProgram(ShaderProgram newProgram){
        program = newProgram;
    }

    public static ShaderProgram getActiveShaderProgram(){
        if (program == null)
            System.err.println("There are no active shaders!");
        return program;
    }
}
