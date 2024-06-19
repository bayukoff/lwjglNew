package ru.cool.lwjgl.utils;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BuffersUtil
{
    public static FloatBuffer storeFloatData(float[] data)
    {
        return BufferUtils.createFloatBuffer(data.length).put(data).flip();
    }

    public static IntBuffer storeIntData(int[] data)
    {
        return BufferUtils.createIntBuffer(data.length).put(data).flip();
    }

    public static DoubleBuffer storeDoubleData(double[] data)
    {
        return BufferUtils.createDoubleBuffer(data.length).put(data).flip();
    }

    public static ByteBuffer storeByteData(byte[] data)
    {
        return BufferUtils.createByteBuffer(data.length).put(data).flip();
    }

    public static FloatBuffer storeFloatData(float data)
    {
        return BufferUtils.createFloatBuffer(1).put(data).flip();
    }

    public static IntBuffer storeIntData(int data)
    {
        return BufferUtils.createIntBuffer(1).put(data).flip();
    }

    public static DoubleBuffer storeDoubleData(double data)
    {
        return BufferUtils.createDoubleBuffer(1).put(data).flip();
    }

    public static ByteBuffer storeByteData(byte data)
    {
        return BufferUtils.createByteBuffer(1).put(data).flip();
    }
}
