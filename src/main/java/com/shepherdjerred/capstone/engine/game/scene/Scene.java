package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.game.scene.element.SceneElement;
import java.util.List;
import java.util.Optional;

public interface Scene {

  void updateState(float interval);

  List<SceneElement> getSceneElements();

  Optional<Scene> getNextScene();
}
