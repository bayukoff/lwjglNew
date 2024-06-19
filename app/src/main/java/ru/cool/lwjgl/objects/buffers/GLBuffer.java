package ru.cool.lwjgl.objects.buffers;

import org.lwjgl.opengl.GL30;

public abstract class GLBuffer {
    protected int bufferIndex;
    public void generateBuffer(){
        this.bufferIndex = GL30.glGenBuffers();
    }
    public abstract void bindBuffer();
    public abstract void unbindBuffer();
    public int getBufferIndex() {
        return bufferIndex;
    }
}
