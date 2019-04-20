package com.shepherdjerred.capstone.engine.game.scenes.lobby.details;

import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.InteractableUIScene;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.network.event.ServerConnectedEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.ShutdownNetworkEvent;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.textbutton.TextButton;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameScene;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.host.SimpleSceneRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import java.util.HashSet;
import java.util.Set;

public class LobbyDetailsScene extends InteractableUIScene {

  private final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public LobbyDetailsScene(EventBus<Event> eventBus,
      ResourceManager resourceManager,
      WindowSize windowSize) {
    super(windowSize,
        resourceManager,
        new SimpleSceneRenderer(resourceManager, windowSize),
        eventBus);
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    createEventHandlerFrame();
    createGameObjects();
  }

  private void createEventHandlerFrame() {
    eventHandlerFrame.registerHandler(ServerConnectedEvent.class, event -> {
    });
  }

  protected Set<GameObject> createGameObjects() {
    Set<GameObject> gameObjects = new HashSet<>();
    var text = new Text(resourceManager,
        "Lobby",
        FontName.M5X7,
        Color.white(),
        24,
        100,
        new WindowRelativeScenePositioner(
            HorizontalPosition.CENTER, VerticalPosition.TOP, new SceneCoordinateOffset(0, 100), 1));

    var backButton = new TextButton(resourceManager,
        windowSize,
        new WindowRelativeScenePositioner(HorizontalPosition.LEFT,
            VerticalPosition.BOTTOM,
            new SceneCoordinateOffset(100, -100),
            1),
        "Back",
        FontName.M5X7,
        Color.white(),
        24,
        new SceneObjectDimensions(100, 50),
        Type.GENERIC,
        () -> {
          var scene = new MainMenuScene(resourceManager,
              eventBus,
              windowSize);
          eventBus.dispatch(new SceneTransitionEvent(scene));
          eventBus.dispatch(new ShutdownNetworkEvent());
        });

    var nextButton = new TextButton(resourceManager,
        windowSize,
        new WindowRelativeScenePositioner(HorizontalPosition.RIGHT,
            VerticalPosition.BOTTOM,
            new SceneCoordinateOffset(-100, -100),
            1),
        "Next",
        FontName.M5X7,
        Color.white(),
        24,
        new SceneObjectDimensions(100, 50),
        Type.GENERIC,
        () -> {
          var scene = new GameScene(resourceManager, eventBus, GameMapName.GRASS, windowSize);
          eventBus.dispatch(new SceneTransitionEvent(scene));
        });

    background = new ParallaxBackground(resourceManager, windowSize,
        ParallaxBackground.Type.random());

    gameObjects.add(text);
    gameObjects.add(backButton);
    gameObjects.add(nextButton);
    return gameObjects;
  }

  @Override
  public void updateState(float interval) {
    super.updateState(interval);
  }

  @Override
  public void initialize() throws Exception {
    super.initialize();
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  @Override
  public void cleanup() {
    super.cleanup();
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }
}
