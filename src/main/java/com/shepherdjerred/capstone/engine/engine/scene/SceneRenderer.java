package com.shepherdjerred.capstone.engine.engine.scene;

/**
 * Renders an entire scene on the screen.
 */
public interface SceneRenderer<T extends Scene> {

  void initialize(T scene) throws Exception;

  void render(T scene);

  void cleanup();
}
