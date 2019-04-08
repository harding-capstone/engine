package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.Getter;

// I have an irrational hatred towards classes named "Manager"
@AllArgsConstructor
public class SceneManager {

  @Getter
  private Scene scene;

  public void initialize() {

  }

  public void update(float interval) {
    scene.updateState(interval);
  }

  public void render() {
    scene.getSceneRenderer().render(scene);
  }

  public void transition(Scene newScene) {
    newScene.initialize();
    var oldScene = scene;
    scene = newScene;
    oldScene.cleanup();
  }
}
