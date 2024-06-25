package ru.cool.lwjgl.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import ru.cool.lwjgl.materials.Uniforms;
import ru.cool.lwjgl.utils.Debug;

import java.nio.FloatBuffer;


public class ShaderProgram
{
    private int vertexShader;
    private int fragmentShader;
    private int shaderProgram;
    public ShaderProgram()
    {

    }
    public ShaderProgram(int vertexShader, int fragmentShader)
    {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }
    public ShaderProgram createProgram(){
        this.shaderProgram = GL30.glCreateProgram();
        if ((vertexShader | fragmentShader) == 0)
            System.out.println("Some Shader is not initialized!");
        GL30.glAttachShader(shaderProgram, vertexShader);
        GL30.glAttachShader(shaderProgram, fragmentShader);
        GL30.glLinkProgram(shaderProgram);
        GL30.glDeleteShader(vertexShader);
        GL30.glDeleteShader(fragmentShader);
        return this;
    }
    public void bindProgram(){
        Shaders.setActiveShaderProgram(this);
        GL30.glUseProgram(this.getShaderProgram());
    }
    public void unbindProgram(){
        Shaders.setActiveShaderProgram(null);
        GL30.glUseProgram(0);
    }
    public int getUniformLocation(String varName)
    {
        if (this.shaderProgram == 0)
            System.out.println("Shader program is not created!");
        return GL30.glGetUniformLocation(this.shaderProgram, varName);
    }
    public void setUniformMatrix4f(Matrix4f matrix, Uniforms uniform){
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer matrixData = stack.mallocFloat(16);
            int uniformLocation = getUniformLocation(uniform.getUniformName());
            checkUniformLocation(uniformLocation);
            GL30.glUniformMatrix4fv(uniformLocation, false, matrix.get(matrixData));
        }
    }

    public void checkProgramErrors(){
        if (GL30.glGetProgrami(shaderProgram, GL30.GL_LINK_STATUS) == GL30.GL_FALSE) {
            System.err.println("Program linking failed: " + GL30.glGetProgramInfoLog(shaderProgram));
        } else {
            System.out.println("Program linked successfully.");
        }

        GL30.glValidateProgram(shaderProgram);
        if (GL30.glGetProgrami(shaderProgram, GL30.GL_VALIDATE_STATUS) == GL30.GL_FALSE) {
            System.err.println("Program validation failed: " + GL30.glGetProgramInfoLog(shaderProgram));
        } else {
            System.out.println("Program validated successfully.");
        }
    }

    public void setUniformVector4f(Vector4f vector, Uniforms uniform){
        int uniformLocation = getUniformLocation(uniform.getUniformName());
        checkUniformLocation(uniformLocation);
        GL30.glUniform4f(uniformLocation, vector.x, vector.y, vector.z, vector.w);
    }
    public boolean setUniformVector3f(Vector3f vector, Uniforms uniform){
        int uniformLocation = getUniformLocation(uniform.getUniformName());
        checkUniformLocation(uniformLocation);
        GL30.glUniform3f(uniformLocation, vector.x, vector.y, vector.z);
        return true;
    }

    private void checkUniformLocation(int uniformLocation){
        if (uniformLocation == -1)
            throw new IllegalArgumentException("Can't find uniform!");
    }
    public void setVertexShader(int vertexShader)
    {
        this.vertexShader = vertexShader;
    }
    public void setFragmentShader(int fragmentShader)
    {
        this.fragmentShader = fragmentShader;
    }
    /*
        return shader program index
     */
    public int getShaderProgram()
    {
        return shaderProgram;
    }
}
