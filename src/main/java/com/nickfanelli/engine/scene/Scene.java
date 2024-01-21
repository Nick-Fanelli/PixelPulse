package com.nickfanelli.engine.scene;

public abstract class Scene {

    public abstract void onCreate();
    public abstract void onUpdate(float deltaTine);
    public abstract void onDestroy();

}
