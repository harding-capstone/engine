package com.shepherdjerred.capstone.engine.game.scenes.mainmenu;

import com.shepherdjerred.capstone.engine.engine.events.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.events.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.events.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.game.objects.button.Button;
import com.shepherdjerred.capstone.engine.game.objects.logo.Logo;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground.Type;
import com.shepherdjerred.capstone.engine.game.objects.button.ButtonRenderer;
import com.shepherdjerred.capstone.engine.game.objects.logo.LogoRenderer;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackgroundRenderer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainMenuScene implements Scene {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  @Getter
  private final List<GameObject> gameObjects;
  private final WindowSize windowSize;
  private final SceneRenderer<MainMenuScene> renderer;

  public MainMenuScene(SceneRenderer<MainMenuScene> renderer,
      ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    this.renderer = renderer;
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.windowSize = windowSize;
    gameObjects = new ArrayList<>();
    createGameObjects();
  }

  private void createGameObjects() {
    var button = new Button(
        new ButtonRenderer(resourceManager),
        new AbsoluteScenePositioner(new SceneCoordinate(410, 410, -10)),
        100,
        100,
        () -> log.info("Hey there!"));

    var logo = new Logo(
        new LogoRenderer(resourceManager),
        new AbsoluteScenePositioner(new SceneCoordinate((windowSize.getWidth() / 2), 50, 0)),
        1.485517919,
        300,
        Logo.Type.GAME);
    logo.setPosition(new RelativeScenePositioner(HorizontalPosition.CENTER,
        VerticalPosition.TOP,
        0,
        50,
        windowSize,
        logo.getWidth(),
        logo.getHeight()));

    var background = new ParallaxBackground(new ParallaxBackgroundRenderer(resourceManager,
        windowSize),
        Type.PLAINS);

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
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

  @Override
  public SceneRenderer getSceneRenderer() {
    return renderer;
  }
}
