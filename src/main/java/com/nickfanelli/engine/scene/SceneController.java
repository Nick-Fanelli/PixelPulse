package com.nickfanelli.engine.scene;

public class SceneController {

    private final Scene scene;

    private boolean isDestroyed = false;

    public SceneController(Scene scene) {
        this.scene = scene;
    }

    public void create() {
        this.scene.onCreate();
    }

    public void update(float deltaTime) {

        if(isDestroyed)
            throw new RuntimeException("Attempting to update a destroyed scene from a scene controller");

        this.scene.onUpdate(deltaTime);
    }

    public void destroy() {
        isDestroyed = true;
        this.scene.onDestroy();
    }

}
