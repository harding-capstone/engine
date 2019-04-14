package com.shepherdjerred.capstone.engine.game.scenes.mainmenu;

import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.scene.position.ObjectRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground.Type;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackgroundRenderer;
import com.shepherdjerred.capstone.engine.game.objects.button.Button;
import com.shepherdjerred.capstone.engine.game.objects.button.ButtonRenderer;
import com.shepherdjerred.capstone.engine.game.objects.logo.Logo;
import com.shepherdjerred.capstone.engine.game.objects.logo.LogoRenderer;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.text.TextRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.singleplayer.SinglePlayerRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.singleplayer.SinglePlayerScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
  private final MainMenuAudio sceneAudio;

  public MainMenuScene(SceneRenderer<MainMenuScene> renderer,
      ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize,
      MainMenuAudio sceneAudio) {
    this.renderer = renderer;
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.windowSize = windowSize;
    this.sceneAudio = sceneAudio;
    gameObjects = new ArrayList<>();
    createGameObjects();
  }

  private void createGameObjects() {
    var logo = new Logo(
        new LogoRenderer(resourceManager),
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.TOP,
            0,
            50,
            0),
        1.485517919,
        300,
        Logo.Type.GAME);

    var button = new Button(new ButtonRenderer(resourceManager),
        new ObjectRelativeScenePositioner(logo, 400, 0, 0, 0, 0),
        100,
        100,
        () -> {
          var scene = new SinglePlayerScene(eventBus,
              resourceManager,
              new SinglePlayerRenderer(resourceManager,
                  eventBus,
                  windowSize));
          eventBus.dispatch(new SceneTransitionEvent(scene));
        });

    var text = new Text(
        new TextRenderer(resourceManager),
        "Castle Casters - Development Build",
        FontName.M5X7,
        Color.white(),
        12,
        new WindowRelativeScenePositioner(HorizontalPosition.RIGHT,
            VerticalPosition.BOTTOM,
            0,
            0,
            0)
    );

    var background = new ParallaxBackground(new ParallaxBackgroundRenderer(resourceManager,
        windowSize),
        randomType());

    gameObjects.add(button);
    gameObjects.add(logo);
    gameObjects.add(text);
    gameObjects.add(background);
  }

  @Override
  public void initialize() throws Exception {
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

    renderer.initialize(this);
    sceneAudio.initialize();
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(gameObject -> gameObject.getRenderer().cleanup());
    renderer.cleanup();
    sceneAudio.cleanup();
  }

  @Override
  public void updateState(float interval) {
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(this);
  }

  private Type randomType() {
    var random = new Random();
    List<Type> types = new ArrayList<>();
    types.add(Type.PLAINS);
    types.add(Type.PURPLE_MOUNTAINS);
    return types.get(random.nextInt(types.size()));
  }
}
