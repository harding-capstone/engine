package com.shepherdjerred.capstone.engine.game.scenes.lobby.list;

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
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerInformation;
import com.shepherdjerred.capstone.engine.game.network.discovery.netty.NettyServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.text.TextRenderer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
  private final Set<ServerInformation> servers;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public LobbyListScene(ParallaxBackground background,
      EventBus<Event> eventBus,
      ResourceManager resourceManager,
      WindowSize windowSize) {
    this.eventBus = eventBus;
    this.resourceManager = resourceManager;
    this.sceneRenderer = new LobbyListRenderer(resourceManager, eventBus, windowSize);
    this.gameObjects = new ArrayList<>();
    gameObjects.add(background);
    discoverer = new NettyServerDiscoverer(eventBus);
    discovererThread = new Thread(discoverer, "DISCOVERER");
    discovererThread.start();
    servers = new HashSet<>();
    eventHandlerFrame = new EventHandlerFrame<>();
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
    var text = new Text(new TextRenderer(resourceManager),
        "Lobby List",
        FontName.M5X7,
        Color.white(),
        12,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.CENTER,
            new SceneCoordinateOffset(0, 0),
            0));

    gameObjects.add(text);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    sceneRenderer.cleanup();
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
