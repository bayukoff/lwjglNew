package ru.cool.lwjgl.window;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import ru.cool.lwjgl.controls.CameraController;
import ru.cool.lwjgl.input.Buttons;
import ru.cool.lwjgl.input.Keyboard;
import ru.cool.lwjgl.input.Mouse;
import ru.cool.lwjgl.materials.StandardMaterial;
import ru.cool.lwjgl.materials.TextureMaterial;
import ru.cool.lwjgl.materials.Uniforms;
import ru.cool.lwjgl.objects.Camera;
import ru.cool.lwjgl.objects.meshes.primitives2d.Quad;
import ru.cool.lwjgl.objects.meshes.primitives2d.Triangle;
import ru.cool.lwjgl.objects.meshes.primitives3d.Cube;
import ru.cool.lwjgl.renderer.MeshRenderer;
import ru.cool.lwjgl.shaders.Shader;
import ru.cool.lwjgl.shaders.ShaderProgram;
import ru.cool.lwjgl.textures.Texture;
import ru.cool.lwjgl.utils.BuffersUtil;
import ru.cool.lwjgl.utils.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Window {
    private final ShortBuffer width = BufferUtils.createShortBuffer(1);
    private final ShortBuffer height = BufferUtils.createShortBuffer(1);
    private final String windowName;
    private long windowId;
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
        Camera camera = new Camera(new Vector3f(0,0,5), 3);
        CameraController cameraController = new CameraController(camera);
        Texture texture = new Texture("textures/dirt.png").loadTexture(true);
        Texture texture1 = new Texture("textures/grassSide.png").loadTexture(true);
        Texture glass = new Texture("textures/glass.png").loadTexture(true);
        Texture daria = new Texture("textures/sprites/daria.png").loadTexture(true);
        daria.createTexture();
        texture1.createTexture();
        texture.createTexture();
        glass.createTexture();
        MeshRenderer meshRenderer = new MeshRenderer(program);
        Triangle triangle = new Triangle(new StandardMaterial(1,0,1));
        Quad quad = new Quad(new TextureMaterial(texture1));
        Quad window = new Quad(new TextureMaterial(glass));
        Quad dariaQuad = new Quad(new TextureMaterial(daria));
        Cube cube = new Cube(new TextureMaterial(texture));
        cube.setPosition(2,0, -2);
        triangle.setPosition(new Vector3f(-0.5f, 0,0));
        window.setPosition(0,0,3);
        meshRenderer.addMeshToRender(quad);
        meshRenderer.addMeshToRender(triangle);
        meshRenderer.addMeshToRender(cube);
        meshRenderer.addMeshToRender(window);
        meshRenderer.addMeshToRender(dariaQuad);

        while (!GLFW.glfwWindowShouldClose(windowId)) {
            if (Keyboard.isButtonPress(Buttons.ESCAPE)){
                break;
            }
            Time.time = GLFW.glfwGetTime();
            GLFW.glfwPollEvents();
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glClearColor(0.8f, 0.75f, 0.92f, 0f);
            GL11.glViewport(0, 0, this.getWidth(), this.getHeight());

            program.bindProgram();
            program.setUniformMatrix4f(camera.getViewMatrix(), Uniforms.VIEW_MATRIX);
            program.setUniformMatrix4f(camera.getProjectionMatrix(), Uniforms.PROJECTION_MATRIX);
            cameraController.invoke();

            quad.setPosition(new Vector3f((float) Math.cos(Time.time * 5), (float)Math.sin(Time.time * 5),-1));
            dariaQuad.setPosition(new Vector3f(0, (float)Math.sin(Time.time * 5),-2));
            meshRenderer.renderMeshes();

            program.unbindProgram();

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            Time.deltaTime = Time.time - Time.lastTime;
            Time.lastTime = Time.time;
            GLFW.glfwSwapBuffers(windowId);
        }
        GL30.glDeleteProgram(program.getShaderProgram());
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
