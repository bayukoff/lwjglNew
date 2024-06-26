package ru.cool.lwjgl.window;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;
import ru.cool.lwjgl.controls.CameraController;
import ru.cool.lwjgl.input.Buttons;
import ru.cool.lwjgl.input.Keyboard;
import ru.cool.lwjgl.input.Mouse;
import ru.cool.lwjgl.objects.Camera;
import ru.cool.lwjgl.objects.buffers.EBO;
import ru.cool.lwjgl.objects.buffers.VAO;
import ru.cool.lwjgl.objects.buffers.VBO;
import ru.cool.lwjgl.objects.meshes.flat.Triangle;
import ru.cool.lwjgl.renderer.MeshRenderer;
import ru.cool.lwjgl.shaders.Shader;
import ru.cool.lwjgl.shaders.ShaderProgram;
import ru.cool.lwjgl.textures.Texture;
import ru.cool.lwjgl.utils.BuffersUtil;
import ru.cool.lwjgl.utils.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

public class Window {
    private final ShortBuffer width = BufferUtils.createShortBuffer(1);
    private final ShortBuffer height = BufferUtils.createShortBuffer(1);
    private final String windowName;
    private long windowId;
    public static double deltaTime;
    private Matrix4f perspective;
    private final FloatBuffer quadVertices = BuffersUtil.storeFloatData(new float[]{
            -1f, -1f, 0f, 1f,
            -1f, 1f, 0f, 0f,
            1f, 1f, 1f, 0f,
            1f, -1f, 1f, 1f
    });
    private final IntBuffer indices = BuffersUtil.storeIntData(new int[]{
       0,1,2,
       0,2,3
    });
    private final FloatBuffer cubeVertices = BuffersUtil.storeFloatData(new float[]{
            -0.5f,0.5f,-0.5f,    0,0,
            -0.5f,-0.5f,-0.5f,   0,1,
            0.5f,-0.5f,-0.5f,    1,1,
            0.5f,0.5f,-0.5f,     1,0,

            -0.5f,0.5f,0.5f,     0,0,
            -0.5f,-0.5f,0.5f,    0,1,
            0.5f,-0.5f,0.5f,     1,1,
            0.5f,0.5f,0.5f,      1,0,

            0.5f,0.5f,-0.5f,     0,0,
            0.5f,-0.5f,-0.5f,    0,1,
            0.5f,-0.5f,0.5f,     1,1,
            0.5f,0.5f,0.5f,      1,0,

            -0.5f,0.5f,-0.5f,    0,0,
            -0.5f,-0.5f,-0.5f,   0,1,
            -0.5f,-0.5f,0.5f,    1,1,
            -0.5f,0.5f,0.5f,     1,0,

            -0.5f,0.5f,0.5f,     0,0,
            -0.5f,0.5f,-0.5f,    0,1,
            0.5f,0.5f,-0.5f,     1,1,
            0.5f,0.5f,0.5f,      1,0,

            -0.5f,-0.5f,0.5f,    0,0,
            -0.5f,-0.5f,-0.5f,   0,1,
            0.5f,-0.5f,-0.5f,    1,1,
            0.5f,-0.5f,0.5f,     1,0
    });
    private final IntBuffer cubeIndices = BuffersUtil.storeIntData(new int[]{
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


    public Window(short width, short height, String windowName) {
        this.width.put(width).flip();
        this.height.put(height).flip();
        this.windowName = windowName;
    }

    public Window(short width, short height) {
        this(width, height, "Default");
    }

    public void updateWindow() {

        Shader vertexShader = new Shader(GL30.GL_VERTEX_SHADER).loadShader("shaders/vertex.vert").createShader();
        Shader fragmentShader = new Shader(GL30.GL_FRAGMENT_SHADER).loadShader("shaders/fragment.frag").createShader();
        ShaderProgram program = new ShaderProgram(vertexShader.getShaderIndex(), fragmentShader.getShaderIndex()).createProgram();
        Keyboard.handleInput(windowId);
        Mouse.handleInput(windowId);

        VAO vao = new VAO();
        vao.generateVAO();
        vao.bindVAO();
        VBO<FloatBuffer> vbo = new VBO<>();
        vbo.generateBuffer();
        vbo.bindBuffer();
        vbo.setBufferData(cubeVertices);
        EBO EBO = new EBO();
        EBO.generateBuffer();
        EBO.bindBuffer();
        EBO.setBufferData(cubeIndices);
        vao.addVertexAttribute(0, 3, 5 * Float.BYTES, 0);
        vao.addVertexAttribute(1, 2, 5 * Float.BYTES, 3 * Float.BYTES);
        vao.enableVertexAttribute(0);
        vao.enableVertexAttribute(1);
        vbo.unbindBuffer();
        vao.unbindVAO();

        Camera camera = new Camera(new Vector3f(0,0,5), 3);
        CameraController cameraController = new CameraController(camera);

        Texture texture = new Texture("textures/dirt.png").loadTexture(true);
        texture.createTexture();

        Matrix4f model = new Matrix4f();
        Matrix4f projection = new Matrix4f().perspective(45, (float) getWidth() / getHeight(), 0.1f, 100f);
        program.bindProgram();

        program.setUniformMatrix4f(model, "model");
        program.setUniformMatrix4f(camera.getViewMatrix(), "view");
        program.setUniformMatrix4f(projection, "projection");
        program.unbindProgram();

        Triangle triangle = new Triangle();
        MeshRenderer renderer = new MeshRenderer(new ArrayList<>(), program);


        while (!GLFW.glfwWindowShouldClose(windowId)) {
            if (Keyboard.isButtonPress(Buttons.ESCAPE)){
                break;
            }
            Time.time = GLFW.glfwGetTime();
            GLFW.glfwPollEvents();
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glClearColor(0.8f, 0.75f, 0.92f, 0f);
            GL11.glViewport(0, 0, this.getWidth(), this.getHeight());
            program.bindProgram();
            cameraController.invoke();
            texture.bindTexture();
            vao.bindVAO();
            camera.setViewMatrix(new Matrix4f().lookAt(new Vector3f(camera.getPosition()), new Vector3f(camera.getPosition()).add(camera.getDirection()), new Vector3f(camera.getUpVector())));
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++){
                    program.setUniformMatrix4f(new Matrix4f().translate(j * 0.5f,0,i * 0.5f), "model");
                    GL30.glDrawElements(GL30.GL_TRIANGLES, cubeIndices.limit(), GL30.GL_UNSIGNED_INT, 0);
                }
            }
            program.setUniformMatrix4f(camera.getViewMatrix(), "view");
            vao.unbindVAO();
            texture.unbindTexture();
            program.unbindProgram();

            GL11.glDisable(GL11.GL_DEPTH_TEST);

            Time.deltaTime = Time.time - Time.lastTime;
            Time.lastTime = Time.time;

            GLFW.glfwSwapBuffers(windowId);
        }
        GLFW.glfwTerminate();
    }

    public void resizeWindow() {
        GLFW.glfwSetWindowSizeCallback(windowId, (window, width, height) -> {
            int[] widthSize = new int[1];
            int[] heightSize = new int[1];
            GLFW.glfwGetFramebufferSize(window, widthSize, heightSize);
            GL30.glViewport(0,0, widthSize[0], heightSize[0]);
        });
    }

    public Window createWindow() {
        boolean init = GLFW.glfwInit();
        if (!init)
            GLFW.glfwTerminate();
        this.windowId = GLFW.glfwCreateWindow(this.getWidth(), this.getHeight(), this.windowName, 0, 0);
        int screenWidth = this.getScreenSize()[0];
        int screenHeight = this.getScreenSize()[1];
        GLFW.glfwSetWindowPos(this.windowId, (screenWidth / 2 - this.getWidth() / 2), (screenHeight / 2 -
                this.getHeight() / 2));
        GLFW.glfwMakeContextCurrent(this.windowId);
        GLFW.glfwSetInputMode(windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLCapabilities glCapabilities = GL.createCapabilities();
        GLFW.glfwSetWindowSize(this.windowId, 640, 480);
        return this;
    }

    public int[] getScreenSize() {
        int screenWidth, screenHeight;
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (vidMode != null) {
            screenWidth = vidMode.width();
            screenHeight = vidMode.height();
            return new int[]{screenWidth, screenHeight};
        }
        return new int[]{1280, 720};
    }

    public short getWidth() {
        return this.width.get(0);
    }

    public short getHeight() {
        return this.height.get(0);
    }
}
