package ru.cool.lwjgl.objects.meshes.flat;

import org.lwjgl.opengl.GL30;
import ru.cool.lwjgl.objects.buffers.EBO;
import ru.cool.lwjgl.objects.buffers.VAO;
import ru.cool.lwjgl.objects.buffers.VBO;
import ru.cool.lwjgl.objects.meshes.GLObjectMesh;
import ru.cool.lwjgl.utils.BuffersUtil;


public class Triangle extends GLObjectMesh {

    public Triangle(){
        this.vertices = BuffersUtil.storeFloatData(new float[]{
                -1f, -1f, 0f,
                0f, 1f, 0f,
                1f, -1f, 0f,
        });
        this.indices = BuffersUtil.storeIntData(new int[]{
                0, 1, 2
        });
    }

    @Override
    public int getDrawingMode() {
        return GL30.GL_TRIANGLES;
    }

    @Override
    public void render() {
        vao.bindVAO();
        GL30.glDrawElements(getDrawingMode(), indices);
        vao.bindVAO();
    }

    @Override
    public void init() {
        vao = new VAO();
        vao.generateVAO();
        vao.bindVAO();
        vbo = new VBO<>();
        vbo.generateBuffer();
        vbo.bindBuffer();
        vbo.setBufferData(vertices);
        ebo = new EBO();
        ebo.generateBuffer();
        ebo.bindBuffer();
        ebo.setBufferData(indices);
        vao.enableVertexAttribute(0);
        vao.addVertexAttribute(0, 3, 3 * Float.BYTES, 0);
        vbo.unbindBuffer();
        vao.unbindVAO();
    }

}
