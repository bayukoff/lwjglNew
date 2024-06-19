package ru.cool.lwjgl.objects.meshes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import ru.cool.lwjgl.objects.buffers.EBO;
import ru.cool.lwjgl.objects.buffers.VAO;
import ru.cool.lwjgl.objects.buffers.VBO;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class GLObjectMesh {
    protected FloatBuffer vertices;
    protected IntBuffer indices;
    protected VAO vao;
    protected VBO<FloatBuffer> vbo;
    protected EBO ebo;
    protected Vector3f position;
    protected Vector3f scale;
    protected Matrix4f modelMatrix = new Matrix4f().identity();
    protected int drawingMode;
    public GLObjectMesh(){

    }

    public abstract void render();
    public abstract void init();

    public void translate(float x, float y, float z){
        modelMatrix.translate(x,y,z);
    }
    public void translate(Vector3f vec){
        this.translate(vec.x, vec.y, vec.z);
    }
    public void rotate(float angle, Vector3f vec){
        this.rotate(angle, vec.x, vec.y, vec.z);
    }
    public void rotate(float angle, float x, float y, float z){
        modelMatrix.rotate(angle, x,y,z);
    }
    public void scale(Vector3f vec){
        this.scale(vec.x, vec.y, vec.z);
    }
    public void scale(float x, float y, float z){
        modelMatrix.scale(x,y,z);
    }
    public FloatBuffer getVertices() {
        return vertices;
    }
    public void setVertices(FloatBuffer vertices) {
        this.vertices = vertices;
    }
    public void setIndices(IntBuffer indices) {
        this.indices = indices;
    }
    public VAO getVao() {
        return vao;
    }
    public void setVao(VAO vao) {
        this.vao = vao;
    }

    public VBO<FloatBuffer> getVbo() {
        return vbo;
    }

    public void setVbo(VBO<FloatBuffer> vbo) {
        this.vbo = vbo;
    }

    public EBO getEbo() {
        return this.ebo;
    }

    public void setEbo(EBO ebo) {
        this.ebo = ebo;
    }

    public int getDrawingMode() {
        return drawingMode;
    }
}
