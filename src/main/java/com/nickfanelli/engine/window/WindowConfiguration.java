package com.nickfanelli.engine.window;

import org.joml.Vector2i;
import org.joml.Vector4f;

import java.awt.*;

public class WindowConfiguration {

    public String title = "";
    public Dimension initialWindowSize = new Dimension(800, 600);

    public Vector4f clearColor = new Vector4f(0.2f, 0.2f, 0.2f, 1.0f);

    public boolean isResizable = true;
    public boolean vSyncEnabled = true;

    public WindowConfiguration() {}
    public WindowConfiguration(String title) { this.title = title; }

}
