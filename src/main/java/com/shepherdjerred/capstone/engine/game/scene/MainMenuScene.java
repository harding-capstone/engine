package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.engine.event.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.game.scene.element.BackgroundSceneElement;
import com.shepherdjerred.capstone.engine.game.scene.element.BackgroundSceneElement.Type;
import com.shepherdjerred.capstone.engine.game.scene.element.ButtonSceneElement;
import com.shepherdjerred.capstone.engine.game.scene.element.Clickable;
import com.shepherdjerred.capstone.engine.game.scene.element.Hoverable;
import com.shepherdjerred.capstone.engine.game.scene.element.LogoSceneElement;
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

    sceneElements.add(new BackgroundSceneElement(new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS));
    sceneElements.add(new ButtonSceneElement(new SceneCoordinate(410, 410, -10),
        100,
        100,
        () -> log.info("Hey there!")));
    sceneElements.add(new LogoSceneElement(new SceneCoordinate(0, 0, 0),
        444,
        300,
        LogoSceneElement.Type.GAME));
  }

  @Override
  public void initialize() {
    eventBus.registerHandler(MouseButtonDownEvent.class,
        mouseButtonDownEvent -> sceneElements.forEach(element -> {
          if (element instanceof Clickable) {
            var orig = mouseButtonDownEvent.getMouseCoordinate();
            var coord = new SceneCoordinate(orig.getX(), orig.getY(), 0);
            if (((Clickable) element).contains(coord)) {
              ((Clickable) element).onClick();
            }
          }
        }));
    eventBus.registerHandler(MouseButtonUpEvent.class,
        mouseButtonUpEvent -> sceneElements.forEach(element -> {
          if (element instanceof Clickable) {
            if (((Clickable) element).isClicked()) {
              ((Clickable) element).onRelease();
            }
          }
        }));
    eventBus.registerHandler(MouseMoveEvent.class,
        mouseMoveEvent -> sceneElements.forEach(element -> {
          var orig = mouseMoveEvent.getNewMousePosition();
          var coord = new SceneCoordinate(orig.getX(), orig.getY(), 0);
          if (element instanceof Hoverable) {
            if (((Hoverable) element).isHovered()) {
              if (!((Hoverable) element).contains(coord)) {
                ((Hoverable) element).onUnhover();
              }
            } else {
              if (((Hoverable) element).contains(coord)) {
                ((Hoverable) element).onHover();
              }
            }
          }
        }));
  }

  @Override
  public void cleanup() {

  }

  @Override
  public void updateState(float interval) {
  }

  @Override
  public Optional<Scene> getNextScene() {
    return Optional.empty();
  }
}
