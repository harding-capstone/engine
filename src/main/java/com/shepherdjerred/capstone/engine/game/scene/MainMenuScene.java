package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.engine.event.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.Background;
import com.shepherdjerred.capstone.engine.game.scene.objects.Background.Type;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button;
import com.shepherdjerred.capstone.engine.game.scene.objects.Clickable;
import com.shepherdjerred.capstone.engine.game.scene.objects.Hoverable;
import com.shepherdjerred.capstone.engine.game.scene.objects.Logo;
import com.shepherdjerred.capstone.engine.game.scene.objects.GameObject;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.BackgroundRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.ButtonRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.LogoRenderer;
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
  private final List<GameObject> gameObjects;
  private final TextureLoader textureLoader;
  private final WindowSize windowSize;

  public MainMenuScene(EventBus<Event> eventBus,
      TextureLoader textureLoader,
      WindowSize windowSize) {
    this.eventBus = eventBus;
    this.textureLoader = textureLoader;
    this.windowSize = windowSize;
    gameObjects = new ArrayList<>();
    createGameObjects();
  }

  private void createGameObjects() {
    var background = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS);
    var button = new Button(
        new ButtonRenderer(textureLoader),
        new SceneCoordinate(410, 410, -10),
        100,
        100,
        () -> log.info("Hey there!"));
    var logo = new Logo(
        new LogoRenderer(textureLoader),
        new SceneCoordinate(0, 0, 0),
        444,
        300,
        Logo.Type.GAME);

    gameObjects.add(background);
    gameObjects.add(button);
    gameObjects.add(logo);
  }

  @Override
  public void initialize() {
    eventBus.registerHandler(MouseButtonDownEvent.class,
        mouseButtonDownEvent -> gameObjects.forEach(element -> {
          if (element instanceof Clickable) {
            var orig = mouseButtonDownEvent.getMouseCoordinate();
            var coord = new SceneCoordinate(orig.getX(), orig.getY(), 0);
            if (((Clickable) element).contains(coord)) {
              ((Clickable) element).onClick();
            }
          }
        }));
    eventBus.registerHandler(MouseButtonUpEvent.class,
        mouseButtonUpEvent -> gameObjects.forEach(element -> {
          if (element instanceof Clickable) {
            if (((Clickable) element).isClicked()) {
              ((Clickable) element).onRelease();
            }
          }
        }));
    eventBus.registerHandler(MouseMoveEvent.class,
        mouseMoveEvent -> gameObjects.forEach(element -> {
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