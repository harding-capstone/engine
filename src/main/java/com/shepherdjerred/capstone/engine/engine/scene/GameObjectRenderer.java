package com.shepherdjerred.capstone.engine.engine.scene;

public interface GameObjectRenderer<T extends GameObject> {

  void init(T sceneElement) throws Exception;

  void render(T sceneElement);

  void cleanup();
}
