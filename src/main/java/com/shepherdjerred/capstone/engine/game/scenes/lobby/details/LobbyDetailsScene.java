package com.shepherdjerred.capstone.engine.game.scenes.lobby.details;

import static com.shepherdjerred.capstone.common.Constants.DISCOVERY_PORT;
import static com.shepherdjerred.capstone.common.Constants.GAME_PORT;

import com.shepherdjerred.capstone.common.player.PlayerInformation;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.InteractableScene;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.event.events.IdentifyPlayerEvent;
import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.event.ServerConnectedEvent;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.textbutton.TextButton;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameScene;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.host.SimpleSceneRenderer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.server.GameServer;
import java.net.InetSocketAddress;
import java.util.UUID;

public class LobbyDetailsScene extends InteractableScene {

  private final EventBus<Event> eventBus;
  private NetworkClient client;
  private GameServer server;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public LobbyDetailsScene(EventBus<Event> eventBus, ResourceManager resourceManager,
      WindowSize windowSize) {
    super(windowSize,
        resourceManager,
        new SimpleSceneRenderer(resourceManager, windowSize),
        eventBus);
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    createEventHandlerFrame();
    createGameObjects();
    createServer();
    createClient();
  }

  private void createEventHandlerFrame() {
    eventHandlerFrame.registerHandler(ServerConnectedEvent.class, event -> {
      eventBus.dispatch(new IdentifyPlayerEvent(new PlayerInformation(UUID.randomUUID(),
          "Jerred")));
    });
  }

  private void createGameObjects() {
    var text = new Text(resourceManager,
        "Lobby",
        FontName.M5X7,
        Color.white(),
        24,
        new WindowRelativeScenePositioner(
            HorizontalPosition.CENTER, VerticalPosition.TOP, new SceneCoordinateOffset(0, 100), 1));

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

    gameObjects.add(text);
    gameObjects.add(nextButton);
  }

  private void createServer() {
    server = new GameServer(new InetSocketAddress(GAME_PORT),
        new InetSocketAddress(DISCOVERY_PORT));
    new Thread(server, "SERVER_THREAD").start();
  }

  private void createClient() {
    client = new NetworkClient(eventBus);
    new Thread(() -> client.connect(new InetSocketAddress("127.0.0.1", GAME_PORT)),
        "NETWORK_CLIENT_THREAD").start();
  }

  @Override
  public void updateState(float interval) {
    super.updateState(interval);
    client.update();
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
