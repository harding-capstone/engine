package com.shepherdjerred.capstone.engine.game.scenes.game;

import static com.shepherdjerred.capstone.engine.game.Constants.RENDER_TILE_RESOLUTION;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.engine.engine.events.input.KeyPressedEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.KeyReleasedEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.input.keyboard.Key;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.map.MapCoordinate;
import com.shepherdjerred.capstone.engine.engine.map.MapTile;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.game.map.MapObject;
import com.shepherdjerred.capstone.engine.game.objects.game.wizard.Wizard;
import com.shepherdjerred.capstone.engine.game.objects.game.wizard.Wizard.State;
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
  private Wizard wizard;
  private final EventHandlerFrame<Event> eventHandlerFrame;
  private final Map<Key, Boolean> pressedKeys;


  public GameScene(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      GameMapName gameMapName,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.gameRenderer = new GameRenderer(resourceManager, eventBus, windowSize);
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
    var inspectTileEventHandler = new EventHandler<MouseButtonDownEvent>() {
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

          log.info(textureCoordinates);

          var texture = (Texture) resourceManager.getUnchecked(tile.getTextureName());
          var resolutionMinX = textureCoordinates.getMinX() * texture.getWidth();
          var resolutionMinY = textureCoordinates.getMinY() * texture.getHeight();
          var resolutionMaxX = textureCoordinates.getMaxX() * texture.getWidth();
          var resolutionMaxY = textureCoordinates.getMaxY() * texture.getHeight();

          var sheetMinX = resolutionMinX / 16;
          var sheetMinY = resolutionMinY / 16;
          var sheetMaxX = resolutionMaxX / 16;
          var sheetMaxY = resolutionMaxY / 16;

          log.info(String.format(
              "img min x: %s, img min y: %s \nimg max x: %s img max y %s \nsheet min x: %s, sheet min y: %s \nsheet max x: %s, sheet max y %s",
              resolutionMinX,
              resolutionMinY,
              resolutionMaxX,
              resolutionMaxY,
              sheetMinX,
              sheetMinY,
              sheetMaxX,
              sheetMaxY));
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

    eventHandlerFrame.registerHandler(MouseButtonDownEvent.class, inspectTileEventHandler);
    eventHandlerFrame.registerHandler(KeyPressedEvent.class, keyDownHandler);
    eventHandlerFrame.registerHandler(KeyReleasedEvent.class, keyUpHandler);
  }

  private void createGameObjects() {
    mapObject = new MapObject(resourceManager, gameMapName);
    wizard = new Wizard(resourceManager,
        new AbsoluteScenePositioner(new SceneCoordinate(0, 0, 100),
            new SceneCoordinateOffset(0, 0)),
        Element.FIRE,
        new SceneObjectDimensions(RENDER_TILE_RESOLUTION * 2, RENDER_TILE_RESOLUTION * 2));
    gameObjects.add(mapObject);
    gameObjects.add(wizard);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    gameRenderer.cleanup();
    resourceManager.free(gameMapName);
  }

  @Override
  public void updateState(float interval) {
    var currMapX = mapObject.getXOffset();
    var currMapY = mapObject.getYOffset();

    var currWizX = wizard.getPosition().getOffset().getXOffset();
    var currWizY = wizard.getPosition().getOffset().getYOffset();

    if (pressedKeys.getOrDefault(Key.W, false)) {
      currWizY -= 10;
      wizard.setState(State.WALKING_UP);
    }
    if (pressedKeys.getOrDefault(Key.A, false)) {
      currWizX -= 10;
      wizard.setState(State.WALKING_LEFT);
    }
    if (pressedKeys.getOrDefault(Key.S, false)) {
      currWizY += 10;
      wizard.setState(State.WALKING_DOWN);
    }
    if (pressedKeys.getOrDefault(Key.D, false)) {
      currWizX += 10;
      wizard.setState(State.WALKING_RIGHT);
    }

    wizard.getPosition().setOffset(new SceneCoordinateOffset(currWizX, currWizY));

    if (pressedKeys.getOrDefault(Key.UP, false)) {
      currMapY += 100;
    }
    if (pressedKeys.getOrDefault(Key.DOWN, false)) {
      currMapY -= 100;
    }
    if (pressedKeys.getOrDefault(Key.LEFT, false)) {
      currMapX += 100;
    }
    if (pressedKeys.getOrDefault(Key.RIGHT, false)) {
      currMapX -= 100;
    }

    mapObject.setXOffset(currMapX);
    mapObject.setYOffset(currMapY);

    gameObjects.forEach(object -> object.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    gameRenderer.render(this);
  }
}
