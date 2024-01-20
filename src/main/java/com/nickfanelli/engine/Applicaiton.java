package com.nickfanelli.engine;

import com.nickfanelli.engine.graphics.Shader;
import com.nickfanelli.engine.window.Window;
import com.nickfanelli.engine.window.WindowConfiguration;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Applicaiton {

    /**
     * The window configuration to be defined when creating the window.
     * @mutableuntil this.launchApplication()
     */
    public WindowConfiguration windowConfiguration = new WindowConfiguration();

    private Window window = null;

    private int currentFPS = 0;
    private FloatBuffer floatBuffer;

    public void launchApplication() {

        this.initializeGLFW();

        this.window = new Window(this.windowConfiguration);
        this.window.createWindow();

        this.onCreate();
        this.update();

        this.window.cleanUp();

        this.cleanUpGLFW();

    }

    private void update() {

        double currentTime, deltaTime;
        double lastTime = glfwGetTime();
        double previousFPSPollTime = glfwGetTime();

        int frameCount = 0;

        while(!glfwWindowShouldClose(window.getWindowPtr())) {

            currentTime = glfwGetTime();
            deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            frameCount++;

            if(currentTime - previousFPSPollTime >= 1.0) {
                this.currentFPS = frameCount;
                frameCount = 0;
                previousFPSPollTime = currentTime;
            }

            this.window.clearWindow();
            this.dispatchUpdate((float) deltaTime);
            this.window.update();

        }

    }

    private Shader shader;

    private float[] vertices = {
            -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
    };

    private int[] indicies = {
            0, 1, 2,
    };

    private int vaoID;
    private int vboID;
    private int iboID;

    private void onCreate() {
        this.shader = new Shader("Shaders/DefaultShader");
        this.shader.compile();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 7, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, Float.BYTES * 7, Float.BYTES * 3);

        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indicies.length);
        indexBuffer.put(indicies).flip();

        iboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    private void dispatchUpdate(float deltaTime) {

        shader.bind();

        glBindVertexArray(vaoID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        Shader.unbind();

    }

    private void initializeGLFW() {

        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

    }

    private void cleanUpGLFW() {

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();

    }

}
