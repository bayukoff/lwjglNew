package ru.cool.lwjgl.objects.meshes.primitives3d;

import org.joml.Vector4f;
import ru.cool.lwjgl.materials.IMaterial;
import ru.cool.lwjgl.materials.StandardMaterial;
import ru.cool.lwjgl.objects.geometry.CubeGeometry;
import ru.cool.lwjgl.objects.meshes.Mesh;

public class Cube extends Mesh {
    public Cube() {
        super(new CubeGeometry());
        this.setMaterial(new StandardMaterial(0,0,0,0));
    }

    public Cube(IMaterial material) {
        super(new CubeGeometry(), material);
    }
}
