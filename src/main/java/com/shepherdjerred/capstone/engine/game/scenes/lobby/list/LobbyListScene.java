package com.shepherdjerred.capstone.engine.game.scenes.lobby.list;

import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneActiveEvent;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.event.handlers.MouseDownClickableHandler;
import com.shepherdjerred.capstone.engine.game.event.handlers.MouseMoveHoverableEventHandler;
import com.shepherdjerred.capstone.engine.game.event.handlers.MouseUpClickableHandler;
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerInformation;
import com.shepherdjerred.capstone.engine.game.network.discovery.event.ServerDiscoveredEvent;
import com.shepherdjerred.capstone.engine.game.network.discovery.netty.NettyServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.textbutton.TextButton;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.host.HostLobbyScene;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LobbyListScene implements Scene {

  private final EventBus<Event> eventBus;
  private final ResourceManager resourceManager;
  private final SceneRenderer<LobbyListScene> sceneRenderer;
  @Getter
  private final List<GameObject> gameObjects;
  private final ServerDiscoverer discoverer;
  private final Thread discovererThread;
  private final EventHandlerFrame<Event> eventHandlerFrame;
  private final Map<ServerInformation, GameObject> serverInfoMap;
  private final WindowSize windowSize;
  private final ParallaxBackground background;

  public LobbyListScene(ParallaxBackground background,
      EventBus<Event> eventBus,
      ResourceManager resourceManager,
      WindowSize windowSize) {
    this.eventBus = eventBus;
    this.windowSize = windowSize;
    this.resourceManager = resourceManager;
    sceneRenderer = new LobbyListRenderer(resourceManager, eventBus, windowSize);
    gameObjects = new ArrayList<>();
    discoverer = new NettyServerDiscoverer();
    discovererThread = new Thread(discoverer, "DISCOVERER");
    eventHandlerFrame = new EventHandlerFrame<>();
    serverInfoMap = new HashMap<>();
    this.background = background;
    gameObjects.add(background);
    createEventHandler();
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  private void createEventHandler() {
    eventHandlerFrame.registerHandler(ServerDiscoveredEvent.class,
        event -> {
          var info = event.getServerInformation();
          var lobby = info.getLobby();
          // TODO load player count
          var string = String.format("Name: %s | Players: %s/%s",
              lobby.getLobbySettings().getName(),
              1,
              2);
          var text = new TextButton(resourceManager,
              windowSize,
              new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
                  VerticalPosition.TOP,
                  new SceneCoordinateOffset(0, serverInfoMap.size() * 60 + 60 + 100),
                  0),
              string,
              FontName.M5X7,
              Color.white(),
              12,
              new SceneObjectDimensions(350, 50),
              Type.GENERIC,
              () -> {
                log.info(info);
                // TODO transition to lobby scene
              });
          try {
            text.initialize();
          } catch (Exception e) {
            e.printStackTrace();
          }
          serverInfoMap.put(info, text);
          gameObjects.add(text);
        });

    eventHandlerFrame.registerHandler(SceneActiveEvent.class, event -> startDiscovery());

    var mouseDownClickable = new MouseDownClickableHandler(this);
    var mouseUpClickable = new MouseUpClickableHandler(this);
    var mouseMoveHoverable = new MouseMoveHoverableEventHandler(this);

    eventHandlerFrame.registerHandler(MouseButtonDownEvent.class, mouseDownClickable);
    eventHandlerFrame.registerHandler(MouseButtonUpEvent.class, mouseUpClickable);
    eventHandlerFrame.registerHandler(MouseMoveEvent.class, mouseMoveHoverable);
  }

  private void startDiscovery() {
    discovererThread.start();
  }

  @Override
  public void initialize() throws Exception {
    createGameObjects();
    sceneRenderer.initialize(this);

    for (GameObject gameObject : gameObjects) {
      gameObject.initialize();
    }
  }

  private void createGameObjects() {
    var text = new Text(resourceManager,
        "Lobby List",
        FontName.M5X7,
        Color.white(),
        24,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.TOP,
            new SceneCoordinateOffset(0, 100),
            0));

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
        });

    var hostButton = new TextButton(resourceManager,
        windowSize,
        new WindowRelativeScenePositioner(HorizontalPosition.RIGHT,
            VerticalPosition.BOTTOM,
            new SceneCoordinateOffset(-100, -100),
            1),
        "Host",
        FontName.M5X7,
        Color.white(),
        24,
        new SceneObjectDimensions(100, 50),
        Type.GENERIC,
        () -> {
          var scene = new HostLobbyScene(background,
              eventBus,
              resourceManager, windowSize, LobbyType.NETWORK);
          eventBus.dispatch(new SceneTransitionEvent(scene));
        });

    gameObjects.add(text);
    gameObjects.add(backButton);
    gameObjects.add(hostButton);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    sceneRenderer.cleanup();
    discoverer.stop();
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }

  @Override
  public void updateState(float interval) {
    var event = discoverer.getEvent();
    event.ifPresent(eventBus::dispatch);
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    sceneRenderer.render(this);
  }
}
