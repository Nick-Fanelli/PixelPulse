package com.nickfanelli.game;

import com.nickfanelli.engine.Applicaiton;

public class EntryPoint {

    public static void main(String[] args) {

        Applicaiton applicaiton = new Applicaiton();
        applicaiton.windowConfiguration.title = "Pixel Pulse";
        applicaiton.windowConfiguration.vSyncEnabled = true;

        applicaiton.launchApplication(GameScene.class, false);

    }

}
