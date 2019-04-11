package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public interface GameObjectRenderer<T extends GameObject> {

  void init(T gameObject) throws Exception;

  void render(WindowSize windowSize, T gameObject);

  void cleanup();
}
