package com.shepherdjerred.capstone.engine.game.rendering;

import com.shepherdjerred.capstone.engine.game.scene.Scene;

public interface SceneRenderer<T extends Scene> {

  void render(T scene);

  void initialize(T scene) throws Exception;

  void cleanup();
}
