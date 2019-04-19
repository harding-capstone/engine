package com.shepherdjerred.capstone.engine.game.scenes.lobby.host;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.lobby.LobbySettings.LobbyType;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.InteractableScene;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.textbutton.TextButton;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.details.LobbyDetailsScene;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HostLobbyScene extends InteractableScene {

  private static final LobbySettings defaultLobbySettings = new LobbySettings("My Lobby",
      new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
      new BoardSettings(9, PlayerCount.TWO),
      LobbyType.LOCAL,
      false);

  private final LobbySettings lobbySettings;

  public HostLobbyScene(ParallaxBackground background,
      EventBus<Event> eventBus,
      ResourceManager resourceManager,
      WindowSize windowSize,
      LobbySettings.LobbyType type) {
    super(windowSize,
        resourceManager,
        new HostLobbyRenderer(resourceManager, eventBus, windowSize),
        eventBus);
    lobbySettings = defaultLobbySettings;
    gameObjects.add(background);
  }

  @Override
  public void initialize() throws Exception {
    createGameObjects();
    super.initialize();
  }

  private void createGameObjects() throws Exception {
    var text = new Text(resourceManager,
        "Lobby Setup",
        FontName.M5X7,
        Color.white(),
        12,
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
          var scene = new LobbyDetailsScene(eventBus,
              resourceManager,
              windowSize);
          eventBus.dispatch(new SceneTransitionEvent(scene));
        });

    gameObjects.add(text);
    gameObjects.add(backButton);
    gameObjects.add(nextButton);
  }
}
