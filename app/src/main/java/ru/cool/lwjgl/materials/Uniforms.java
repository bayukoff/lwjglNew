package ru.cool.lwjgl.materials;

public enum Uniforms {
    COLOR("meshColor"),
    MODEL_MATRIX("model"),
    VIEW_MATRIX("view"),
    PROJECTION_MATRIX("projection")
    ;

    private final String uniformName;
    Uniforms(String uniformName){
        this.uniformName = uniformName;
    }

    public String getUniformName() {
        return uniformName;
    }
}
