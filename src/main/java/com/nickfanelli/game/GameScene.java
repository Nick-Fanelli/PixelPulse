package com.nickfanelli.game;

import com.nickfanelli.engine.scene.Scene;

public class GameScene extends Scene {

    private boolean health;

    public GameScene(Boolean health) {
        this.health = health;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onUpdate(float deltaTine) {
        System.out.println("Hey");
    }

    @Override
    public void onDestroy() {

    }

}
