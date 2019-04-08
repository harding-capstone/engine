package com.shepherdjerred.capstone.engine.engine.scene;

/**
 * Renders an entire scene on the screen.
 */
public interface SceneRenderer<T extends Scene> {

  void render(T scene);

  void initialize(T scene) throws Exception;

  void cleanup();
}
