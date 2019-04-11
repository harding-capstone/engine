package com.shepherdjerred.capstone.engine.engine.events.handlers.scene;

import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.scene.SceneManager;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SceneTransitionEventHandler implements EventHandler<SceneTransitionEvent> {

  private final SceneManager sceneManager;

  @Override
  public void handle(SceneTransitionEvent sceneTransitionEvent) {
    try {
      sceneManager.transition(sceneTransitionEvent.getNewScene());
    } catch (Exception e) {
      throw new RuntimeException("Error transitioning scenes");
    }
  }
}
