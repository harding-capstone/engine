package com.shepherdjerred.capstone.engine.game.scenes.lobby;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
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

  private final EventBus<Event> eventBus;
  private final ResourceManager resourceManager;
  private final SceneRenderer<LobbyScene> sceneRenderer;
  @Getter
  private final List<GameObject> gameObjects;
  private LobbySettings lobbySettings;
  private Thread gameServerThread;

  public LobbyScene(ParallaxBackground background,
      EventBus<Event> eventBus,
      ResourceManager resourceManager,
      SceneRenderer<LobbyScene> sceneRenderer) {
    this.eventBus = eventBus;
    this.resourceManager = resourceManager;
    this.sceneRenderer = sceneRenderer;
    this.gameObjects = new ArrayList<>();
    gameObjects.add(background);
  }

  @Override
  public void initialize() throws Exception {
    gameServerThread = new Thread(() -> {
      var gameServer = new GameServer(new LobbySettings("My Lobby",
          new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
          new BoardSettings(9, PlayerCount.TWO),
          LobbyType.LOCAL,
          false));
      var connector = new NettyServerConnector(new NettyServerSettings("127.0.0.1",
          35567));
      gameServer.registerConnector(connector);
      try {
        gameServer.run();
      } catch (InterruptedException e) {
        log.error("Error while running server.", e);
      }
    });
    gameServerThread.start();

    var text = new Text(new TextRenderer(resourceManager),
        "Lobby Setup",
        FontName.M5X7,
        Color.white(),
        12,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.CENTER,
            0,
            0,
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
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    sceneRenderer.render(this);
  }
}
