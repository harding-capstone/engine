package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import java.util.List;

public interface Scene {

  void makeActive();

  void initialize();

  void cleanup();

  void updateState(float interval);

  List<GameObject> getGameObjects();

  SceneRenderer getSceneRenderer();
}
