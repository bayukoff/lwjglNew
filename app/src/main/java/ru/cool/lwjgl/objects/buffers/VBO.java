package ru.cool.lwjgl.objects.buffers;

import org.lwjgl.opengl.GL30;

import java.nio.*;

public class VBO<B extends Buffer> extends GLBuffer{

    private B dataBuffer;
    @Override
    public void bindBuffer() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.bufferIndex);
    }
    @Override
    public void unbindBuffer() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }

    public void setBufferData(B buffer){
        dataBuffer = buffer;
        if (buffer instanceof FloatBuffer)
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, (FloatBuffer) buffer, GL30.GL_STATIC_DRAW);
        else if (buffer instanceof IntBuffer)
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, (IntBuffer) buffer, GL30.GL_STATIC_DRAW);
        else if (buffer instanceof ByteBuffer)
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, (ByteBuffer) buffer, GL30.GL_STATIC_DRAW);
        else if (buffer instanceof LongBuffer)
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, (LongBuffer) buffer, GL30.GL_STATIC_DRAW);
        else if (buffer instanceof DoubleBuffer)
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, (DoubleBuffer) buffer, GL30.GL_STATIC_DRAW);
        else
            throw new IllegalArgumentException("Unsupported buffer");

    }
    public void setData(B data){
        this.dataBuffer = data;
    }
    public B getData() {
        return dataBuffer;
    }
}