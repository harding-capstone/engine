package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.engine.event.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.Background;
import com.shepherdjerred.capstone.engine.game.scene.objects.Background.Type;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button;
import com.shepherdjerred.capstone.engine.game.scene.objects.Logo;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.BackgroundRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.ButtonRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.LogoRenderer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainMenuScene implements Scene {

  private final EventBus<Event> eventBus;
  @Getter
  private final List<GameObject> gameObjects;
  private final TextureLoader textureLoader;
  private final FontLoader fontLoader;
  private final WindowSize windowSize;
  private final SceneRenderer<MainMenuScene> renderer;

  public MainMenuScene(SceneRenderer<MainMenuScene> renderer,
      EventBus<Event> eventBus,
      TextureLoader textureLoader,
      FontLoader fontLoader,
      WindowSize windowSize) {
    this.renderer = renderer;
    this.eventBus = eventBus;
    this.fontLoader = fontLoader;
    this.textureLoader = textureLoader;
    this.windowSize = windowSize;
    gameObjects = new ArrayList<>();
    createGameObjects();
  }

  private void createGameObjects() {
    var button = new Button(
        new ButtonRenderer(textureLoader),
        new SceneCoordinate(410, 410, -10),
        100,
        100,
        () -> log.info("Hey there!"));
    var logo = new Logo(
        new LogoRenderer(textureLoader),
        new SceneCoordinate((windowSize.getWidth() / 2), 50, 0),
        1.485517919,
        300,
        Logo.Type.GAME);

    createBackground();
    createBackgroundWithOffsets();
    createBackgroundWithOffsets2();
    gameObjects.add(button);
    gameObjects.add(logo);
  }

  private void createBackground() {
    var backgroundA = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_A);
    var backgroundB = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_B);
    var backgroundC = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_C);
    var backgroundD = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_D);
    var backgroundE = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_E);
    gameObjects.add(backgroundA);
    gameObjects.add(backgroundB);
    gameObjects.add(backgroundC);
    gameObjects.add(backgroundD);
    gameObjects.add(backgroundE);
  }

  private void createBackgroundWithOffsets() {
    var backgroundA = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_A);
    var backgroundB = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_B);
    var backgroundC = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_C);
    var backgroundD = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_D);
    var backgroundE = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_E);
    backgroundA.setPosition(new SceneCoordinate(windowSize.getWidth(), 0, 0));
    backgroundB.setPosition(new SceneCoordinate(windowSize.getWidth(), 0, 0));
    backgroundC.setPosition(new SceneCoordinate(windowSize.getWidth(), 0, 0));
    backgroundD.setPosition(new SceneCoordinate(windowSize.getWidth(), 0, 0));
    backgroundE.setPosition(new SceneCoordinate(windowSize.getWidth(), 0, 0));
    gameObjects.add(backgroundA);
    gameObjects.add(backgroundB);
    gameObjects.add(backgroundC);
    gameObjects.add(backgroundD);
    gameObjects.add(backgroundE);
  }

  private void createBackgroundWithOffsets2() {
    var backgroundA = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_A);
    var backgroundB = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_B);
    var backgroundC = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_C);
    var backgroundD = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_D);
    var backgroundE = new Background(
        new BackgroundRenderer(textureLoader, windowSize),
        new SceneCoordinate(0, 0, 0),
        Type.PURPLE_MOUNTAINS_E);
    backgroundA.setPosition(new SceneCoordinate(windowSize.getWidth() * 2, 0, 0));
    backgroundB.setPosition(new SceneCoordinate(windowSize.getWidth() * 2, 0, 0));
    backgroundC.setPosition(new SceneCoordinate(windowSize.getWidth() * 2, 0, 0));
    backgroundD.setPosition(new SceneCoordinate(windowSize.getWidth() * 2, 0, 0));
    backgroundE.setPosition(new SceneCoordinate(windowSize.getWidth() * 2, 0, 0));
    gameObjects.add(backgroundA);
    gameObjects.add(backgroundB);
    gameObjects.add(backgroundC);
    gameObjects.add(backgroundD);
    gameObjects.add(backgroundE);
  }

  @Override
  public void initialize() {
    eventBus.registerHandler(MouseButtonDownEvent.class,
        mouseButtonDownEvent -> gameObjects.forEach(element -> {
          if (element instanceof Clickable) {
            var orig = mouseButtonDownEvent.getMouseCoordinate();
            var coord = new SceneCoordinate(orig.getX(), orig.getY(), 0);
            if (((Clickable) element).contains(coord)) {
              ((Clickable) element).onClickBegin();
            }
          }
        }));

    eventBus.registerHandler(MouseButtonUpEvent.class,
        mouseButtonUpEvent -> gameObjects.forEach(element -> {
          if (element instanceof Clickable) {
            if (((Clickable) element).isClicked()) {
              ((Clickable) element).onClickEnd();
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
    gameObjects.forEach(gameObject -> {
      if (gameObject instanceof Background) {
        var background = (Background) gameObject;
        var pos = background.getPosition();
        float step;
        var type = background.getType();
        switch (type) {
          case PURPLE_MOUNTAINS_A:
            step = 0f;
            break;
          case PURPLE_MOUNTAINS_B:
            step = .2f;
            break;
          case PURPLE_MOUNTAINS_C:
            step = .6f;
            break;
          case PURPLE_MOUNTAINS_D:
            step = 1.2f;
            break;
          case PURPLE_MOUNTAINS_E:
            step = 2.4f;
            break;
          default:
            step = 0;
            break;
        }
        var newPos = new SceneCoordinate(pos.getX() - step, pos.getY(), pos.getZ());
        if (newPos.getX() < windowSize.getWidth() * -1) {
          newPos = new SceneCoordinate(windowSize.getWidth() * 2, pos.getY(), pos.getZ());
        }
        background.setPosition(newPos);
      }
    });
  }

  @Override
  public SceneRenderer getSceneRenderer() {
    return renderer;
  }
}
