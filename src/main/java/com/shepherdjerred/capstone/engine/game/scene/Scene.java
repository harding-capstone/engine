package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.game.scene.objects.GameObject;
import java.util.List;
import java.util.Optional;

public interface Scene {

  void initialize();

  void cleanup();

  void updateState(float interval);

  List<GameObject> getGameObjects();

  Optional<Scene> getNextScene();
}
