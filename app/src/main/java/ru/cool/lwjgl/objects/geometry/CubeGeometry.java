package ru.cool.lwjgl.objects.geometry;

import ru.cool.lwjgl.utils.BuffersUtil;

public class CubeGeometry extends Geometry{

    public CubeGeometry() {
        super();
    }

    @Override
    public void initMeshData() {
        this.vertices = BuffersUtil.storeFloatData(new float[]{
                -1f,1f,-1f,   0,0,
                -1f,-1f,-1f,  0,1,
                1f,-1f,-1f,   1,1,
                1f,1f,-1f,    1,0,

                -1f,1f,1f,    0,0,
                -1f,-1f,1f,   0,1,
                1f,-1f,1f,    1,1,
                1f,1f,1f,     1,0,

                1f,1f,-1f,    0,0,
                1f,-1f,-1f,   0,1,
                1f,-1f,1f,    1,1,
                1f,1f,1f,     1,0,

                -1f,1f,-1f,   0,0,
                -1f,-1f,-1f,  0,1,
                -1f,-1f,1f,   1,1,
                -1f,1f,1f,    1,0,

                -1f,1f,1f,    0,0,
                -1f,1f,-1f,   0,1,
                1f,1f,-1f,    1,1,
                1f,1f,1f,     1,0,

                -1f,-1f,1f,   0,0,
                -1f,-1f,-1f,  0,1,
                1f,-1f,-1f,   1,1,
                1f,-1f,1f,    1,0
        });
        this.indices = BuffersUtil.storeIntData(new int[]{
                0,1,3,
                3,1,2,

                4,5,7,
                7,5,6,

                8,9,11,
                11,9,10,

                12,13,15,
                15,13,14,

                16,17,19,
                19,17,18,

                20,21,23,
                23,21,22
        });
    }

    @Override
    public void initVertexAttributes() {
        initStandardAttributes();
    }

}
