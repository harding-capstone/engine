package com.shepherdjerred.capstone.engine.game.scenes.lobby;

import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.network.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.netty.NettyClientSettings;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.text.TextRenderer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.server.GameServer;
import com.shepherdjerred.capstone.server.network.netty.NettyServerConnector;
import com.shepherdjerred.capstone.server.network.netty.NettyServerSettings;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LobbyScene implements Scene {

  private static final LobbySettings defaultLobbySettings = new LobbySettings("My Lobby",
      new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
      new BoardSettings(9, PlayerCount.TWO),
      LobbyType.LOCAL,
      false);
  private static final String localHostname = "127.0.0.1";
  private static final int port = 35567;

  private final EventBus<Event> eventBus;
  private final ResourceManager resourceManager;
  private final SceneRenderer<LobbyScene> sceneRenderer;
  @Getter
  private final List<GameObject> gameObjects;
  private Thread gameServerThread;
  private NetworkClient networkClient;
  private Lobby lobby;

  public LobbyScene(ParallaxBackground background,
      EventBus<Event> eventBus,
      ResourceManager resourceManager,
      SceneRenderer<LobbyScene> sceneRenderer) {
    this.eventBus = eventBus;
    this.resourceManager = resourceManager;
    this.sceneRenderer = sceneRenderer;
    this.gameObjects = new ArrayList<>();
    gameObjects.add(background);
    lobby = Lobby.from(defaultLobbySettings);
  }

  @Override
  public void initialize() throws Exception {
    createServer();
    createClient();
    createGameObjects();
  }

  private void createServer() {
    gameServerThread = new Thread(() -> {
      try {
        log.info("Running server");
        var gameServer = new GameServer(defaultLobbySettings);
        var connector = new NettyServerConnector(new NettyServerSettings(localHostname,
            port));
        gameServer.registerConnector(connector);
        gameServer.run();
      } catch (InterruptedException e) {
        log.error("Error while running server.", e);
      }
    }, "GAME_SERVER");
    gameServerThread.start();
  }

  private void createClient() {
    networkClient = new NetworkClient(eventBus);
    networkClient.connect(new NettyClientSettings(localHostname, port));
  }

  private void createGameObjects() throws Exception {
    var text = new Text(new TextRenderer(resourceManager),
        "Lobby Setup",
        FontName.M5X7,
        Color.white(),
        12,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.CENTER,
            new SceneCoordinateOffset(0, 0),
            0));

    gameObjects.add(text);
    sceneRenderer.initialize(this);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    sceneRenderer.cleanup();
    // TODO better way to stop thread?
    gameServerThread.stop();
  }

  @Override
  public void updateState(float interval) {
    networkClient.handleLatestEvent();
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    sceneRenderer.render(this);
  }
}
