package ru.cool.lwjgl.objects.geometry;

import ru.cool.lwjgl.utils.BuffersUtil;

public class QuadGeometry extends Geometry{
    public QuadGeometry() {
        super();
    }

    @Override
    public void initMeshData() {
        this.vertices = BuffersUtil.storeFloatData(new float[]{
                -1, -1, 0,  0,0,
                -1, 1, 0,   0,1,
                1, 1, 0,    1,1,
                1, -1, 0,   1,0,
        });
        this.indices = BuffersUtil.storeIntData(new int[]{
                0,1,2,
                0,2,3
        });
    }

    @Override
    public void initVertexAttributes() {
        initStandardAttributes();
    }

}
