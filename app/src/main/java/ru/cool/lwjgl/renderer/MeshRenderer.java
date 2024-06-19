package ru.cool.lwjgl.renderer;

import ru.cool.lwjgl.objects.meshes.GLObjectMesh;
import ru.cool.lwjgl.shaders.ShaderProgram;

import java.util.List;

public class MeshRenderer {

    private final List<GLObjectMesh> objects;
    private final ShaderProgram shaderProgram;
    public MeshRenderer(List<GLObjectMesh> objects, ShaderProgram shaderProgram) {
        this.objects = objects;
        this.shaderProgram = shaderProgram;
    }

    public void renderObjects(){
        shaderProgram.bindProgram();
        for (GLObjectMesh obj : objects){
            obj.render();
        }
        shaderProgram.unbindProgram();
    }

    public void renderObject(GLObjectMesh obj){
        shaderProgram.bindProgram();
        obj.render();
        shaderProgram.unbindProgram();
    }
}
