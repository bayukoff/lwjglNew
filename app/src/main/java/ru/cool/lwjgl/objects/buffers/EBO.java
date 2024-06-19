package ru.cool.lwjgl.objects.buffers;

import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;

public class EBO extends GLBuffer {
    private IntBuffer dataBuffer;
    public void setData(IntBuffer data){
        this.dataBuffer = data;
    }
    public IntBuffer getData() {
        return dataBuffer;
    }
    @Override
    public void bindBuffer() {
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.bufferIndex);
    }
    @Override
    public void unbindBuffer() {
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void setBufferData(IntBuffer buffer) {
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
        this.dataBuffer = buffer;
    }
}
