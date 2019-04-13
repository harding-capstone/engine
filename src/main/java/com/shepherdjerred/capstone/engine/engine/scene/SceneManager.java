package com.shepherdjerred.capstone.engine.engine.scene;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

// I have an irrational hatred towards classes named "Manager"
@Log4j2
public class SceneManager {

  @Getter
  private Scene scene;
  private boolean isTransitioning;

  public SceneManager(Scene scene) {
    this.scene = scene;
  }

  public void initialize() {
  }

  public void update(float interval) {
    scene.updateState(interval);
  }

  public void render() {
    scene.getSceneRenderer().render(scene);
  }

  public void transition(Scene newScene) throws Exception {
    if (!isTransitioning) {
      isTransitioning = true;
      var oldScene = scene;
      newScene.initialize();
      newScene.getSceneRenderer().initialize(newScene);
      scene = newScene;
      oldScene.cleanup();
      isTransitioning = false;
    } else {
      log.info("Ignoring transition because one is already in progress");
    }
  }

  public void cleanup() {
    scene.cleanup();
    scene.getSceneRenderer().cleanup();
  }
}
