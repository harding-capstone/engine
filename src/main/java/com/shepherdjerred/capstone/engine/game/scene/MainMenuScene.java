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
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePosition.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePosition.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button;
import com.shepherdjerred.capstone.engine.game.scene.objects.Logo;
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
        new AbsoluteScenePosition(new SceneCoordinate(410, 410, -10)),
        100,
        100,
        () -> log.info("Hey there!"));
    var logo = new Logo(
        new LogoRenderer(textureLoader),
        new AbsoluteScenePosition(new SceneCoordinate((windowSize.getWidth() / 2), 50, 0)),
        1.485517919,
        200,
        Logo.Type.GAME);
    logo.setPosition(new RelativeScenePosition(HorizontalPosition.CENTER,
        VerticalPosition.TOP,
        0,
        100,
        windowSize,
        logo.getWidth(),
        logo.getHeight()));

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
//    gameObjects.forEach(gameObject -> {
//      if (gameObject instanceof Background) {
//        var background = (Background) gameObject;
//        var pos = background.getPosition();
//        float step;
//        var type = background.getType();
//        switch (type) {
//          case PURPLE_MOUNTAINS_B:
//            step = .2f;
//            break;
//          case PURPLE_MOUNTAINS_C:
//            step = .6f;
//            break;
//          case PURPLE_MOUNTAINS_D:
//            step = 1.2f;
//            break;
//          case PURPLE_MOUNTAINS_E:
//            step = 2.4f;
//            break;
//          default:
//            step = 0;
//            break;
//        }
//        var newPos = new SceneCoordinate(pos.getX() - step, pos.getY(), pos.getZ());
//        if (newPos.getX() < windowSize.getWidth() * -1) {
//          newPos = new SceneCoordinate(windowSize.getWidth() * 2, pos.getY(), pos.getZ());
//        }
//        background.setPosition(newPos);
//      }
//    });
  }

  @Override
  public SceneRenderer getSceneRenderer() {
    return renderer;
  }
}
