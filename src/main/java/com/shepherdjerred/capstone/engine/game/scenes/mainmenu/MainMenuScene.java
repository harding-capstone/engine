package com.shepherdjerred.capstone.engine.game.scenes.mainmenu;

import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ObjectRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.handlers.MouseDownClickableHandler;
import com.shepherdjerred.capstone.engine.game.handlers.MouseMoveHoverableEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.MouseUpClickableHandler;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground.Type;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackgroundRenderer;
import com.shepherdjerred.capstone.engine.game.objects.logo.Logo;
import com.shepherdjerred.capstone.engine.game.objects.logo.LogoRenderer;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.text.TextRenderer;
import com.shepherdjerred.capstone.engine.game.objects.textbutton.TextButton;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.singleplayer.SinglePlayerLobbyRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.singleplayer.SinglePlayerLobbyScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
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
  private final MainMenuAudio sceneAudio;
  private final EventHandlerFrame<Event> handlerFrame;

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
    this.handlerFrame = new EventHandlerFrame<>();
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
        200,
        Logo.Type.GAME);

    var background = new ParallaxBackground(new ParallaxBackgroundRenderer(resourceManager,
        windowSize),
        Type.random());

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

    var buttonSize = new SceneObjectDimensions(300, 50);

    var singlePlayerButton = new TextButton(resourceManager,
        windowSize,
        new ObjectRelativeScenePositioner(logo, 0, 0, 0, 0, 0),
        "Single Player",
        FontName.M5X7,
        Color.white(),
        12,
        buttonSize,
        () -> {
          var scene = new SinglePlayerLobbyScene(background,
              eventBus,
              resourceManager,
              new SinglePlayerLobbyRenderer(resourceManager,
                  eventBus,
                  windowSize));
          eventBus.dispatch(new SceneTransitionEvent(scene));
        });

    var multiPlayerButton = new TextButton(resourceManager,
        windowSize,
        new ObjectRelativeScenePositioner(singlePlayerButton, 0, 0, 0, 0, 0),
        "Multiplayer",
        FontName.M5X7,
        Color.white(),
        12,
        buttonSize,
        () -> {
        });

    var optionsButton = new TextButton(resourceManager,
        windowSize,
        new ObjectRelativeScenePositioner(multiPlayerButton, 100, 0, 0, 0, 0),
        "Options",
        FontName.M5X7,
        Color.white(),
        12,
        buttonSize,
        () -> {
        });

    var aboutButton = new TextButton(resourceManager,
        windowSize,
        new ObjectRelativeScenePositioner(optionsButton, 100, 0, 0, 0, 0),
        "About",
        FontName.M5X7,
        Color.white(),
        12,
        buttonSize,
        () -> {
        });

    var helpButton = new TextButton(resourceManager,
        windowSize,
        new ObjectRelativeScenePositioner(aboutButton, 100, 0, 0, 0, 0),
        "Help",
        FontName.M5X7,
        Color.white(),
        12,
        buttonSize,
        () -> {
        });

    gameObjects.add(singlePlayerButton);
    gameObjects.add(multiPlayerButton);
    gameObjects.add(optionsButton);
    gameObjects.add(helpButton);
    gameObjects.add(aboutButton);
    gameObjects.add(logo);
    gameObjects.add(text);
    gameObjects.add(background);
  }

  @Override
  public void initialize() throws Exception {
    var mouseDownClickable = new MouseDownClickableHandler(this);
    var mouseUpClickable = new MouseUpClickableHandler(this);
    var mouseMoveHoverable = new MouseMoveHoverableEventHandler(this);

    handlerFrame.registerHandler(MouseButtonDownEvent.class, mouseDownClickable);
    handlerFrame.registerHandler(MouseButtonUpEvent.class, mouseUpClickable);
    handlerFrame.registerHandler(MouseMoveEvent.class, mouseMoveHoverable);
    eventBus.registerHandlerFrame(handlerFrame);

    renderer.initialize(this);
    sceneAudio.initialize();
  }

  @Override
  public void cleanup() {
    // TODO remove event handlers
    gameObjects.forEach(GameObject::cleanup);
    renderer.cleanup();
    sceneAudio.cleanup();
    eventBus.removeHandlerFrame(handlerFrame);
  }

  @Override
  public void updateState(float interval) {
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(this);
  }
}
