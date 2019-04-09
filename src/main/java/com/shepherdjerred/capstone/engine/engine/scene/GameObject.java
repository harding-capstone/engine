package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePosition;

public interface GameObject {

  ScenePosition getPosition();

  void setPosition(ScenePosition scenePosition);

  GameObjectRenderer getRenderer();
}
