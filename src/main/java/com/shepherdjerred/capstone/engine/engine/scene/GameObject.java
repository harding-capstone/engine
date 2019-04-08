package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;

public interface GameObject {

  SceneCoordinate getPosition();

  void setPosition(SceneCoordinate sceneCoordinate);

  GameObjectRenderer getRenderer();
}
