package ru.cool.lwjgl.objects.geometry;

import ru.cool.lwjgl.objects.buffers.EBO;
import ru.cool.lwjgl.objects.buffers.VAO;
import ru.cool.lwjgl.objects.buffers.VBO;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Geometry {
    protected FloatBuffer vertices;
    protected IntBuffer indices;
    protected VAO vao;
    protected VBO<FloatBuffer> vbo;
    protected EBO ebo;

    public Geometry(){
        this.initMeshData();
        this.initGLObjects();
        this.initVertexAttributes();
    }

    /**
     * Initialization of mesh vertices and indexes
     */
    public abstract void initMeshData();

    /**
     * Adding vertex attributes. Before adding it, you need to bind vbo and ebo
     */
    public abstract void initVertexAttributes();

    public void initStandardAttributes(){
        vao.bindVAO();
        vbo.bindBuffer();
        ebo.bindBuffer();
        vao.enableVertexAttribute(0);
        ebo.setBufferData(indices);
        vao.addVertexAttribute(0, 3, 5 * Float.BYTES, 0);
        vao.enableVertexAttribute(1);
        vao.addVertexAttribute(1, 2, 5 * Float.BYTES, 3 * Float.BYTES);
        vbo.unbindBuffer();
        vao.unbindVAO();
    }
    public void initGLObjects(){
        vao = new VAO();
        vbo = new VBO<>();
        ebo = new EBO();

        vao.generateVAO();
        vao.bindVAO();
        vbo.generateBuffer();
        vbo.bindBuffer();
        vbo.setBufferData(vertices);
        ebo.generateBuffer();

        vbo.unbindBuffer();
        vao.unbindVAO();
    }
    public VAO getVao() {
        return vao;
    }

    public VBO<FloatBuffer> getVbo() {
        return vbo;
    }

    public EBO getEbo() {
        return ebo;
    }

    public FloatBuffer getVertices() {
        return vertices;
    }

    public IntBuffer getIndices() {
        return indices;
    }

}
