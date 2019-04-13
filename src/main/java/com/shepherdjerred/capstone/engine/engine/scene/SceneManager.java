package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.engine.events.scene.SceneActiveEvent;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

// I have an irrational hatred towards classes named "Manager"
@Log4j2
public class SceneManager {

  private final EventBus<Event> eventBus;
  @Getter
  private Scene scene;
  private boolean isTransitioning;

  public SceneManager(EventBus<Event> eventBus, Scene scene) {
    this.eventBus = eventBus;
    this.scene = scene;
  }

  public void initialize() throws Exception {
    scene.initialize();
    scene.getSceneRenderer().initialize(scene);
    if (scene.getSceneAudio() != null) {
      scene.getSceneAudio().initialize();
    }
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
      newScene.getSceneAudio().initialize();
      scene = newScene;
      oldScene.cleanup();
      isTransitioning = false;
      eventBus.dispatch(new SceneActiveEvent());
    } else {
      log.info("Ignoring transition because one is already in progress");
    }
  }

  public void cleanup() {
    scene.cleanup();
    scene.getSceneRenderer().cleanup();
    if (scene.getSceneAudio() != null) {
      scene.getSceneAudio().cleanup();
    }
  }
}
