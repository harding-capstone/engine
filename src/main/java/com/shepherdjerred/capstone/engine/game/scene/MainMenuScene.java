package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.game.scene.element.ButtonSceneElement;
import com.shepherdjerred.capstone.engine.game.scene.element.SceneElement;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainMenuScene implements Scene {

  private final EventBus<Event> eventBus;
  @Getter
  private final List<SceneElement> sceneElements;

  public MainMenuScene(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    sceneElements = new ArrayList<>();
    sceneElements.add(new ButtonSceneElement(new SceneCoordinate(0, 0, 0), 300, 100));
  }

  @Override
  public void updateState(float interval) {
  }

  @Override
  public Optional<Scene> getNextScene() {
    return Optional.empty();
  }
}
