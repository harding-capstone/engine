package com.shepherdjerred.capstone.engine.game.scenes.help;

import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.InteractableUIScene;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.textbutton.TextButton;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.host.SimpleSceneRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;

public class HelpScene extends InteractableUIScene {

  private final EventBus eventBus;

  public HelpScene(ResourceManager resourceManager,
      WindowSize windowSize,
      EventBus<Event> eventBus) {
    super(windowSize,
        resourceManager,
        new SimpleSceneRenderer(resourceManager, windowSize),
        eventBus);
    this.eventBus = eventBus;
    createGameObjects();
  }

  private void createGameObjects() {
    var text = new Text(resourceManager,
        "Castle Casters is a board game where the goal is to get your witch or wizard to the "
            + "other side.\nWhat makes Castle Casters special is your ability to cast magical walls!\n\n"
            + "Making a Turn: On any turn, a player may either cast a wall or move their wizard.\n"
            + "Placing a Wall: Walls are two spaces across and can be used to block the opposing "
            + "wizard.\nYou cannot block any wizard from reaching the goal completely, but you may "
            + "use walls to make their route much longer.\nEach player has 10 walls.\nTo cast a wall, "
            + "click and drag from the start of the wall to the end.\n\n"
            + "Moving the Wizard: Wizards can be moved up, down, left, or right.\nThey cannot be "
            + "moved diagonally unless they are jumping.\n\n"
            + "Jumping Rules: When you find yourself face to face with an opponent, you may jump them.\n"
            + "If there is a wall or other obstruction behind your opponent, you may jump "
            + "diagonally to the left or right of the opponent's wizard.\n",
        FontName.M5X7,
        Color.white(),
        12,
        500,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.CENTER,
            new SceneCoordinateOffset(0, 0),
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

    background = new ParallaxBackground(resourceManager, windowSize,
        ParallaxBackground.Type.random());

    gameObjects.add(text);
    gameObjects.add(backButton);
  }
}
