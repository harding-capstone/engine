package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.game.scene.objects.GameObject;

public interface ObjectRenderer<T extends GameObject> {

  void init(T sceneElement);

  void render(T sceneElement);

  void cleanup();
}
