package ru.cool.lwjgl.renderer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;
import ru.cool.lwjgl.materials.IMaterial;
import ru.cool.lwjgl.materials.TextureMaterial;
import ru.cool.lwjgl.materials.Uniforms;
import ru.cool.lwjgl.objects.geometry.Geometry;
import ru.cool.lwjgl.objects.meshes.Mesh;
import ru.cool.lwjgl.shaders.ShaderProgram;
import ru.cool.lwjgl.utils.Debug;

import java.util.*;

public class MeshRenderer {
    private final Map<Class<? extends Mesh>, List<Mesh>> meshesMap;
    private final ShaderProgram program;

    public MeshRenderer(ShaderProgram program){
        meshesMap = new HashMap<>();
        this.program = program;
    }

    public void startRenderMeshes(){
        program.bindProgram();
        this.applyMaterials();
        program.unbindProgram();
    }

    public void renderMeshes(){
        Set<Class<? extends Mesh>> keys = meshesMap.keySet();
        program.bindProgram();
        for (Class<? extends Mesh> key : keys){
            List<Mesh> meshList = meshesMap.get(key);
            for (int i = 0; i < meshList.size(); i++){
                Mesh mesh = meshList.get(i);
                Geometry geom = mesh.getGeometry();
                Matrix4f modelMatrix = mesh.getModelMatrix();
                program.setUniformMatrix4f(modelMatrix, Uniforms.MODEL_MATRIX);
                geom.getVao().bindVAO();
                geom.getVao().enableVertexAttributes();
                geom.getEbo().bindBuffer();

                mesh.getMaterial().applyMaterial();
                GL30.glDrawElements(GL30.GL_TRIANGLES, geom.getIndices().capacity(), GL30.GL_UNSIGNED_INT, 0);
                if (mesh.getMaterial() instanceof TextureMaterial)
                    ((TextureMaterial) mesh.getMaterial()).unbindTextures();
                geom.getVao().disableVertexAttributes();
                geom.getVao().unbindVAO();
            }
        }
        program.unbindProgram();
    }

    private void applyMaterials(){
        Set<Class<? extends Mesh>> keys = meshesMap.keySet();
        for (Class<? extends Mesh> key : keys){
            List<Mesh> meshList = meshesMap.get(key);
            for (int i = 0; i < meshList.size(); i++){
                Mesh mesh = meshList.get(i);
                if (mesh.getMaterial() == null)
                    continue;
                IMaterial material = mesh.getMaterial();
                material.applyMaterial();
            }
        }
    }

    public void addMeshToRender(Mesh mesh){
        if (meshesMap.containsKey(mesh.getClass()))
            getMeshesList(mesh.getClass()).add(mesh);

        else{
            List<Mesh> meshesList = new ArrayList<>();
            meshesList.add(mesh);
            meshesMap.put(mesh.getClass(), meshesList);
        }
    }

    public List<Mesh> getMeshesList(Class<? extends Mesh> meshClass) {
        return meshesMap.get(meshClass);
    }

    public void setMeshesList(Class<? extends Mesh> meshClass, List<Mesh> meshesList) {
        meshesMap.put(meshClass, meshesList);
    }
}
