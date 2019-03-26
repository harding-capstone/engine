package com.shepherdjerred.capstone.engine.game.scene.element;

public interface SceneElementRenderer<T extends SceneElement> {

  void init(T sceneElement);

  void render(T sceneElement);

  void cleanup();
}
