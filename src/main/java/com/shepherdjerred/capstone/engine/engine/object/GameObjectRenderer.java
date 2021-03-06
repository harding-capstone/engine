package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public interface GameObjectRenderer<T extends GameObject> {

  void initialize(T gameObject) throws Exception;

  void render(WindowSize windowSize, T gameObject);

  void cleanup();
}
