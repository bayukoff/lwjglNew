package ru.cool.lwjgl.objects.buffers;

import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class VAO {
    private int id;
    private final ArrayList<Integer> vertexAttributePointers = new ArrayList<>();
    public void enableVertexAttributes(){
        this.vertexAttributePointers.forEach(GL30::glEnableVertexAttribArray);
    }
    public void disableVertexAttributes(){
        this.vertexAttributePointers.forEach(GL30::glDisableVertexAttribArray);
    }
    public void addVertexAttribute(int index, int size, int stride, int pointer){
        this.vertexAttributePointers.add(index);
        GL30.glVertexAttribPointer(index, size, GL30.GL_FLOAT, false, stride, pointer);
    }
    public void addVertexAttributeOneTime(int index, int size, int stride, int pointer){
        this.vertexAttributePointers.add(index);
        GL30.glEnableVertexAttribArray(index);
        GL30.glVertexAttribPointer(index, size, GL30.GL_FLOAT, false, stride, pointer);
        GL30.glDisableVertexAttribArray(index);
    }
    public void enableVertexAttribute(int index){
        GL30.glEnableVertexAttribArray(index);
    }
    public void disableVertexAttribute(int index){
        GL30.glDisableVertexAttribArray(index);
    }
    public void generateVAO(){
        this.id = GL30.glGenVertexArrays();
    }
    public void bindVAO(){
        GL30.glBindVertexArray(this.id);
    }
    public static void bindVAO(int vaoId){
        GL30.glBindVertexArray(vaoId);
    }
    public void unbindVAO(){
        GL30.glBindVertexArray(0);
    }
    public int getAttribBufferBindig(int index){
        return GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ARRAY_BUFFER_BINDING);
    }
    public int getAttribEnabled(int index){
        return GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ATTRIB_ARRAY_ENABLED);
    }
    public int getAttribArraySize(int index){
        return GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ATTRIB_ARRAY_SIZE);
    }
    public int getAttribArrayStride(int index){
        return GL30.glGetVertexAttribIi(index, GL30.GL_VERTEX_ATTRIB_ARRAY_STRIDE);
    }

    public int getId() {
        return id;
    }
}