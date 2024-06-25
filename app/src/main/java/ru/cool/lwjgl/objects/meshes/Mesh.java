package ru.cool.lwjgl.objects.meshes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.cool.lwjgl.materials.IMaterial;
import ru.cool.lwjgl.objects.geometry.Geometry;

public abstract class Mesh {
    private Vector3f position;
    private Vector3f scale;
    private Vector3f rotate;
    private Matrix4f modelMatrix;
    private IMaterial material;

    private final Geometry geometry;

    public Mesh(Geometry geometry){
        this.geometry = geometry;
        this.position = new Vector3f(0,0,0);
        this.scale = new Vector3f(1,1,1);
        this.rotate = new Vector3f(0,0,0);
        this.modelMatrix = new Matrix4f();
    }

    public Mesh(Geometry geometry, IMaterial material){
        this(geometry);
        this.material = material;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public IMaterial getMaterial() {
        return material;
    }

    public void setMaterial(IMaterial material) {
        this.material = material;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        this.updateMatrix();
    }


    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        updateMatrix();
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        this.updateMatrix();
    }

    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
        updateMatrix();
    }

    public void setScale(float uniformScale) {
        this.scale.set(uniformScale, uniformScale, uniformScale);
        updateMatrix();
    }

    public Vector3f getRotate() {
        return rotate;
    }

    public void setRotate(Vector3f rotate) {
        this.rotate = rotate;
        this.updateMatrix();
    }

    public void setRotate(float x, float y, float z) {
        this.rotate.set(x, y, z);
        updateMatrix();
    }
    public void updateMatrix(){
        modelMatrix.identity()
                .translate(position)
                .rotateX(rotate.x)
                .rotateY(rotate.y)
                .rotateZ(rotate.z)
                .scale(scale);
    }


    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

}
