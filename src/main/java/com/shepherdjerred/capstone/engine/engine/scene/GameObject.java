package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.ObjectRenderer;

public interface GameObject {

  SceneCoordinate getPosition();

  void setPosition(SceneCoordinate sceneCoordinate);

  ObjectRenderer getRenderer();
}
