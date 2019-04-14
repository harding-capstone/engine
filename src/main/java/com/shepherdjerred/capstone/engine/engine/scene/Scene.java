package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import java.util.List;

public interface Scene {

  void initialize() throws Exception;

  void cleanup();

  void updateState(float interval);

  List<GameObject> getGameObjects();

  void render(WindowSize windowSize);

}
