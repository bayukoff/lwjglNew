package ru.cool.lwjgl.objects.meshes.primitives2d;

import org.joml.Vector4f;
import ru.cool.lwjgl.materials.IMaterial;
import ru.cool.lwjgl.materials.StandardMaterial;
import ru.cool.lwjgl.objects.geometry.QuadGeometry;
import ru.cool.lwjgl.objects.meshes.Mesh;

public class Quad extends Mesh {
    public Quad() {
        super(new QuadGeometry());
        this.setMaterial(new StandardMaterial(new Vector4f(0,0,0,1)));
    }

    public Quad(IMaterial material) {
        super(new QuadGeometry(), material);
    }
}
