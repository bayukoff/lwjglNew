package ru.cool.lwjgl.objects.geometry;

import ru.cool.lwjgl.utils.BuffersUtil;

public class TriangleGeometry extends Geometry{

    @Override
    public void initMeshData() {
        this.vertices = BuffersUtil.storeFloatData(new float[]{
                -1, -1, 0,  0,0,
                0, 1, 0,    0.5f, 1,
                1, -1, 0,   1,0
        });
        this.indices = BuffersUtil.storeIntData(new int[]{
                0,1,2
        });
    }

    @Override
    public void initVertexAttributes() {
        initStandardAttributes();
    }
}
