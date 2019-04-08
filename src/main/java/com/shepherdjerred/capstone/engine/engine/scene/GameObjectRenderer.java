package com.shepherdjerred.capstone.engine.engine.scene;

public interface GameObjectRenderer<T extends GameObject> {

  void init(T sceneElement);

  void render(T sceneElement);

  void cleanup();
}
