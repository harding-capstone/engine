package com.shepherdjerred.capstone.engine.game.scene.element.rendering;

import com.shepherdjerred.capstone.engine.game.scene.element.SceneElement;

public interface SceneElementRenderer<T extends SceneElement> {

  void init(T sceneElement);

  void render(T sceneElement);

  void cleanup();
}
