package com.shepherdjerred.capstone.engine.game.scenes.game;

import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.map.MapObject;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class GameScene implements Scene {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private final GameRenderer gameRenderer;
  @Getter
  private final List<GameObject> gameObjects;
  private final WindowSize windowSize;
  private final GameMapName gameMapName;

  public GameScene(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      GameRenderer gameRenderer,
      GameMapName gameMapName,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.gameRenderer = gameRenderer;
    this.gameObjects = new ArrayList<>();
    this.windowSize = windowSize;
    this.gameMapName = gameMapName;
    createGameObjects();
  }

  @Override
  public void initialize() throws Exception {
    gameRenderer.initialize(this);
    for (GameObject gameObject : gameObjects) {
      gameObject.initialize();
    }
  }

  private void createGameObjects() {
    var map = new MapObject(resourceManager, gameMapName);
    gameObjects.add(map);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    gameRenderer.cleanup();
    resourceManager.free(gameMapName);
  }

  @Override
  public void updateState(float interval) {

  }

  @Override
  public void render(WindowSize windowSize) {
    gameRenderer.render(this);
  }
}
