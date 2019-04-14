package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public interface GameObject {

  void initialize() throws Exception;

  void cleanup();

  SceneObjectDimensions getSceneObjectDimensions();

  ScenePositioner getPosition();

  void setPosition(ScenePositioner scenePositioner);

  void render(WindowSize windowSize);

  void update(float interval);
}
