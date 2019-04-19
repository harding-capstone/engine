package com.shepherdjerred.capstone.engine.game.scenes.teamintro;

import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.AbstractUIScene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.logo.Logo;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TeamIntroScene extends AbstractUIScene {

  private final EventBus<Event> eventBus;
  private float time = 0;
  private boolean hasTransitioned = false;

  public TeamIntroScene(SceneRenderer<TeamIntroScene> renderer,
      ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    super(resourceManager,
        windowSize,
        new TeamIntroRenderer(resourceManager, eventBus, windowSize));
    this.eventBus = eventBus;
    createGameObjects();
  }

  private void createGameObjects() {
    var logo = new Logo(resourceManager,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.CENTER,
            new SceneCoordinateOffset(0, 0),
            100),
        1.102292769,
        300,
        Logo.Type.TEAM);

    gameObjects.add(logo);
  }

  @Override
  public void updateState(float interval) {
    super.updateState(interval);
    time += interval;
    if (time > 5 && !hasTransitioned) {
      eventBus.dispatch(new SceneTransitionEvent(new MainMenuScene(
          resourceManager,
          eventBus,
          windowSize)));
      hasTransitioned = true;
    }
  }
}
