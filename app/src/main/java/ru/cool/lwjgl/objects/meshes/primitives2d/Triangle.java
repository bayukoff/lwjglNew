package ru.cool.lwjgl.objects.meshes.primitives2d;

import ru.cool.lwjgl.materials.IMaterial;
import ru.cool.lwjgl.materials.StandardMaterial;
import ru.cool.lwjgl.objects.geometry.TriangleGeometry;
import ru.cool.lwjgl.objects.meshes.Mesh;

public class Triangle extends Mesh {

    public Triangle() {
        super(new TriangleGeometry());
        this.setMaterial(new StandardMaterial(0,0,0,0));
    }

    public Triangle(IMaterial material) {
        super(new TriangleGeometry(), material);
    }


}
