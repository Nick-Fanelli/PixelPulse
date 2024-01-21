package com.nickfanelli.engine.scene;

import java.lang.reflect.Constructor;

public class SceneManager {

    private SceneController sceneController = null;

    public SceneManager() {}

    public SceneManager(Class<?> sceneClass, Object... constructorArgs) {
        this.setScene(sceneClass, constructorArgs);
    }

    public void setScene(Class<?> sceneClass, Object... constructorArgs) {

        Scene newScene;

        try {

            Class<?>[] constructorTypes = new Class<?>[constructorArgs.length];

            for(int i = 0; i < constructorTypes.length; i++) {
                constructorTypes[i] = constructorArgs[i].getClass();

                if(constructorTypes[i] == Integer.class && constructorArgs[i] instanceof Integer) {
                    constructorTypes[i] = int.class;
                }
            }

            Constructor<?> constructor = sceneClass.getConstructor(constructorTypes);
            newScene = (Scene) constructor.newInstance(constructorArgs);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Constructor Argument Mismatch for creating scene: " + sceneClass.getName(), e);
        } catch(Exception e) {
            throw new RuntimeException("Problem creating the new scene", e);
        }

        this.changeScene(newScene);

    }

    public void dispatchUpdate(float deltaTime) {

        if(this.sceneController == null)
            return;

        this.sceneController.update(deltaTime);

    }

    private void changeScene(Scene scene) {

        if(this.sceneController != null) {
            this.sceneController.destroy();
            this.sceneController = null;
        }

        scene.onCreate();

        this.sceneController = new SceneController(scene);

    }

}
