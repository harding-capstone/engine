package com.shepherdjerred.capstone.engine.game.scenes.game;

import static com.shepherdjerred.capstone.engine.game.Constants.RENDER_TILE_RESOLUTION;

import com.shepherdjerred.capstone.engine.engine.events.input.KeyPressedEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.KeyReleasedEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.input.keyboard.Key;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.map.MapCoordinate;
import com.shepherdjerred.capstone.engine.engine.map.MapTile;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.map.MapObject;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameScene implements Scene {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private final GameRenderer gameRenderer;
  @Getter
  private final List<GameObject> gameObjects;
  private final WindowSize windowSize;
  private final GameMapName gameMapName;
  private MapObject mapObject;
  private final EventHandlerFrame<Event> eventHandlerFrame;
  private final Map<Key, Boolean> pressedKeys;


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
    this.eventHandlerFrame = new EventHandlerFrame<>();
    pressedKeys = new HashMap<>();
    createGameObjects();
  }

  @Override
  public void initialize() throws Exception {
    gameRenderer.initialize(this);
    for (GameObject gameObject : gameObjects) {
      gameObject.initialize();
    }
    createEventHandlerFrame();
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  private void createEventHandlerFrame() {
    var clickEventHandler = new EventHandler<MouseButtonDownEvent>() {
      @Override
      public void handle(MouseButtonDownEvent mouseButtonDownEvent) {
        var tileSize = RENDER_TILE_RESOLUTION;
        var x = (mouseButtonDownEvent.getMouseCoordinate().getX() / tileSize);
        var y = (mouseButtonDownEvent.getMouseCoordinate().getY() / tileSize);
        Set<MapTile> matchingTiles = new HashSet<>();
        mapObject.getMapLayers().forEach(layer -> {
          var tile = layer.getTile(new MapCoordinate(x, y));
          tile.ifPresent(matchingTiles::add);
        });
        log.info(matchingTiles);
        matchingTiles.forEach(tile -> {
          var textureCoordinates = tile.getTextureSheetCoordinates();

          var texture = (Texture) resourceManager.getUnchecked(tile.getTextureName());
          var resX = textureCoordinates.getMinX() * texture.getWidth();
          var resY = textureCoordinates.getMinY() * texture.getHeight();
          var sheetX = resX / 16;
          var sheetY = resY / 16;

          log.info(String.format("img x: %s, img y: %s. sheet x: %s, sheet y: %s",
              resX,
              resY,
              sheetX,
              sheetY));
        });
      }
    };

    var keyDownHandler = new EventHandler<KeyPressedEvent>() {
      @Override
      public void handle(KeyPressedEvent keyPressedEvent) {
        pressedKeys.put(keyPressedEvent.getKey(), true);
      }
    };

    var keyUpHandler = new EventHandler<KeyReleasedEvent>() {
      @Override
      public void handle(KeyReleasedEvent keyReleasedEvent) {
        pressedKeys.remove(keyReleasedEvent.getKey());
      }
    };

    eventHandlerFrame.registerHandler(MouseButtonDownEvent.class, clickEventHandler);
    eventHandlerFrame.registerHandler(KeyPressedEvent.class, keyDownHandler);
    eventHandlerFrame.registerHandler(KeyReleasedEvent.class, keyUpHandler);
  }

  private void createGameObjects() {
    mapObject = new MapObject(resourceManager, gameMapName);
    gameObjects.add(mapObject);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    gameRenderer.cleanup();
    resourceManager.free(gameMapName);
  }

  @Override
  public void updateState(float interval) {
    var currY = mapObject.getYOffset();
    var currX = mapObject.getXOffset();

    if (pressedKeys.getOrDefault(Key.UP, false)) {
      mapObject.setYOffset(currY + 100);
    }
    if (pressedKeys.getOrDefault(Key.DOWN, false)) {
      mapObject.setYOffset(currY - 100);
    }
    if (pressedKeys.getOrDefault(Key.LEFT, false)) {
      mapObject.setXOffset(currX + 100);
    }
    if (pressedKeys.getOrDefault(Key.RIGHT, false)) {
      mapObject.setXOffset(currX - 100);
    }
  }

  @Override
  public void render(WindowSize windowSize) {
    gameRenderer.render(this);
  }
}
