package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;

public interface GameObject {

  ScenePositioner getPosition();

  void setPosition(ScenePositioner scenePositioner);

  GameObjectRenderer getRenderer();

  void update(float interval);
}
