package com.nickfanelli.engine.window;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    public final WindowConfiguration windowConfiguration;

    private long window = NULL;

    public Window(WindowConfiguration windowConfiguration) {
        this.windowConfiguration = windowConfiguration;
    }

    public void createWindow() {

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, this.windowConfiguration.isResizable ? GLFW_TRUE : GLFW_FALSE);

        this.window = glfwCreateWindow(this.windowConfiguration.initialWindowSize.width, this.windowConfiguration.initialWindowSize.height, this.windowConfiguration.title, NULL, NULL);

        if(this.window == NULL)
            throw new RuntimeException("Failed to create GLFW Window");

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth    = stack.mallocInt(1);
            IntBuffer pHeight   = stack.mallocInt(1);

            glfwGetWindowSize(this.window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidMode != null;

            glfwSetWindowPos(
                    this.window,
                    ( vidMode.width()  - pWidth.get(0)  )   / 2,
                    ( vidMode.height() - pHeight.get(0) )   / 2
            );

        }

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(this.windowConfiguration.vSyncEnabled ? 1 : 0);

        glfwShowWindow(this.window);

        GL.createCapabilities();

        glClearColor(
                this.windowConfiguration.clearColor.x, this.windowConfiguration.clearColor.y,
                this.windowConfiguration.clearColor.z, this.windowConfiguration.clearColor.w
        );

        glfwFocusWindow(window);
    }

    public void clearWindow() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void cleanUp() {

        if(this.window == NULL)
            throw new RuntimeException("Can not clean up window that is equal to NULL");

        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);

    }

    public long getWindowPtr() { return this.window; }

}
