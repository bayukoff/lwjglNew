package ru.cool.lwjgl.materials;


import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.cool.lwjgl.shaders.ShaderProgram;
import ru.cool.lwjgl.shaders.Shaders;
import ru.cool.lwjgl.utils.Debug;

public class StandardMaterial implements IMaterial{



    private Vector4f color;

    public StandardMaterial(Vector4f color) {
        this.color = color;
    }

    public StandardMaterial(Vector3f color){
        this(new Vector4f(color.x,color.y,color.z,1));
    }

    public StandardMaterial(float r, float g, float b){
        this(new Vector4f(r,g,b,1));
    }

    public StandardMaterial(float r, float g, float b, float a){
        this(new Vector4f(r,g,b,a));
    }

    public Vector4f getColor() {
        return color;
    }
    public void setColor(Vector4f color) {
        this.color = color;
    }

    @Override
    public void applyMaterial(){
        ShaderProgram program = Shaders.getActiveShaderProgram();
        program.setUniformVector4f(this.color, Uniforms.COLOR);
    }
}
