package com.shepherdjerred.capstone.engine.game.scenes.game;

import static com.shepherdjerred.capstone.engine.game.Constants.RENDER_TILE_RESOLUTION;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.engine.engine.events.input.KeyPressedEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.KeyReleasedEvent;
import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.input.keyboard.Key;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseButton;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.map.MapCoordinate;
import com.shepherdjerred.capstone.engine.engine.map.MapToQuoridorConverter;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.position.MapCoordinateScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.event.events.DoTurnEvent;
import com.shepherdjerred.capstone.engine.game.event.events.TryDoTurnEvent;
import com.shepherdjerred.capstone.engine.game.objects.game.map.MapObject;
import com.shepherdjerred.capstone.engine.game.objects.game.wall.Wall;
import com.shepherdjerred.capstone.engine.game.objects.game.wizard.Wizard;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallLocation;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.HashMap;
import java.util.HashSet;
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
  private final Set<GameObject> gameObjects;
  private final WindowSize windowSize;
  private final GameMapName gameMapName;
  private MapObject mapObject;
  private Match match;
  private final EventHandlerFrame<Event> eventHandlerFrame;
  private final Map<Key, Boolean> pressedKeys;
  private final Map<QuoridorPlayer, Wizard> wizards;

  public GameScene(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      GameMapName gameMapName,
      Match match,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.gameRenderer = new GameRenderer(resourceManager, eventBus, windowSize);
    this.gameObjects = new HashSet<>();
    this.windowSize = windowSize;
    this.gameMapName = gameMapName;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    this.match = match;
    this.wizards = new HashMap<>();
    pressedKeys = new HashMap<>();
    createGameObjects();
  }

  @Override
  public void initialize() throws Exception {
    var players = match.getMatchSettings().getPlayerCount();
    for (int i = 1; i < players.toInt() + 1; i++) {
      var player = QuoridorPlayer.fromInt(i);
      var boardPos = match.getBoard().getPawnLocation(player);
      var converter = new MapToQuoridorConverter();
      var mapPos = converter.convert(boardPos);
      var wizard = new Wizard(resourceManager,
          new MapCoordinateScenePositioner(new SceneCoordinateOffset(0, 0), mapPos, 10),
          Element.FIRE,
          new SceneObjectDimensions(32, 32));
      wizards.put(player, wizard);
    }

    gameObjects.addAll(wizards.values());

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

      }
    };

    var turnHandler = new EventHandler<DoTurnEvent>() {
      @Override
      public void handle(DoTurnEvent event) {
        var turn = event.getTurn();
        var player = match.getActivePlayerId();
        match = match.doTurn(turn);

        var converter = new MapToQuoridorConverter();

        if (turn instanceof MovePawnTurn) {
          var boardPos = match.getBoard().getPawnLocation(player);
          var mapPos = converter.convert(boardPos);
          var wizard = wizards.get(player);
          wizard.setPosition(new MapCoordinateScenePositioner(new SceneCoordinateOffset(0, 0),
              mapPos,
              10));
        }
        if (turn instanceof PlaceWallTurn) {
          // TO create wall and position it
          var pos = ((PlaceWallTurn) turn).getLocation();
          var coord1 = pos.getFirstCoordinate();
          var coord2 = pos.getSecondCoordinate();
          var coord3 = pos.getVertex();
          var mapCoord1 = converter.convert(coord1);
          var mapCoord2 = converter.convert(coord2);
          var mapCoord3 = converter.convert(coord3);
          var wall1 = new Wall(resourceManager, new SceneObjectDimensions(24, 24),
              new MapCoordinateScenePositioner(new SceneCoordinateOffset(0, 0), mapCoord1, 10));
          var wall2 = new Wall(resourceManager, new SceneObjectDimensions(24, 24),
              new MapCoordinateScenePositioner(new SceneCoordinateOffset(0, 0), mapCoord2, 10));
          var wall3 = new Wall(resourceManager, new SceneObjectDimensions(24, 24),
              new MapCoordinateScenePositioner(new SceneCoordinateOffset(0, 0), mapCoord3, 10));

          try {
            wall1.initialize();
            wall2.initialize();
            wall3.initialize();
          } catch (Exception e) {
            e.printStackTrace();
          }

          gameObjects.add(wall1);
          gameObjects.add(wall2);
          gameObjects.add(wall3);
        }
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

    eventHandlerFrame.registerHandler(DoTurnEvent.class, turnHandler);
    eventHandlerFrame.registerHandler(MouseButtonDownEvent.class, inspectTileEventHandler);
    eventHandlerFrame.registerHandler(KeyPressedEvent.class, keyDownHandler);
    eventHandlerFrame.registerHandler(KeyReleasedEvent.class, keyUpHandler);
    eventHandlerFrame.registerHandler(MouseButtonDownEvent.class, (event) -> {
      var converter = new MapToQuoridorConverter();

      var tileSize = RENDER_TILE_RESOLUTION;
      var x = (event.getMouseCoordinate().getX() / tileSize);
      var y = (event.getMouseCoordinate().getY() / tileSize);

      var pos = converter.convert(new MapCoordinate(x, y));

      if (pos == null) {
        return;
      }

      var player = match.getActivePlayerId();
      var source = match.getBoard().getPawnLocation(player);
      Turn turn;

      if (event.getButton() == MouseButton.LEFT) {
        turn = new NormalMovePawnTurn(player, source, pos);
      } else if (event.getButton() == MouseButton.RIGHT) {
        if (pressedKeys.getOrDefault(Key.V, false)) {
          var posX = pos.getX();
          var posY = pos.getY();
          turn = new PlaceWallTurn(player,
              new WallLocation(new Coordinate(posX, posY),
                  new Coordinate(posX, posY + 1),
                  new Coordinate(posX, posY + 2)));
        } else {
          var posX = pos.getX();
          var posY = pos.getY();
          turn = new PlaceWallTurn(player,
              new WallLocation(new Coordinate(posX, posY),
                  new Coordinate(posX + 1, posY),
                  new Coordinate(posX + 2, posY)));
        }
      } else {
        return;
      }

      eventBus.dispatch(new TryDoTurnEvent(turn));
    });
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
    gameObjects.forEach(object -> object.update(interval));
  }

  @Override
  public void render(WindowSize windowSize) {
    gameRenderer.render(this);
  }
}
