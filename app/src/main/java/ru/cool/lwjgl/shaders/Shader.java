package ru.cool.lwjgl.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Shader {

    public int shaderType;

    private int shaderIndex;
    public StringBuilder code = new StringBuilder();

    public Shader(int shaderType) {
        this.shaderType = shaderType;
    }

    public Shader createShader()
    {
        shaderIndex = GL30.glCreateShader(shaderType);
        if(code.isEmpty())
            System.out.println("Shader code is not compiled!");
        GL30.glShaderSource(shaderIndex, code);
        GL30.glCompileShader(shaderIndex);
        if(isShaderCompile(shaderIndex))
            System.out.println("Shader compiled successfully!");
        return this;
    }

    public Shader loadShader(String shader){
        InputStream stream = getClass().getClassLoader().getResourceAsStream(shader);
        if (stream == null) {
            System.err.println("File not found!");
            return null;
        }
        try (ReadableByteChannel channel = Channels.newChannel(stream)) {
            ByteBuffer buffer = BufferUtils.createByteBuffer(stream.available());
            channel.read(buffer);
            buffer.flip();
            for (int i = 0; i < buffer.capacity(); i++) {
                code.append((char)buffer.get(i));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    private boolean isShaderCompile(int shaderIndex)
    {
        int shaderStatusCode = GL30.glGetShaderi(shaderIndex, GL30.GL_COMPILE_STATUS);
        String shaderStatus = GL30.glGetShaderInfoLog(shaderIndex);
        return shaderStatusCode == 1;
    }

    public int getShaderIndex()
    {
        return shaderIndex;
    }


}

