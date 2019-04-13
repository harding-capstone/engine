package com.shepherdjerred.capstone.engine.game.scenes.teamintro;

import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.RelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.logo.Logo;
import com.shepherdjerred.capstone.engine.game.objects.logo.LogoRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TeamIntroScene implements Scene {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  @Getter
  private final List<GameObject> gameObjects;
  private final WindowSize windowSize;
  private final SceneRenderer<TeamIntroScene> renderer;
  private float time = 0;
  private boolean hasTransitioned = false;

  public TeamIntroScene(SceneRenderer<TeamIntroScene> renderer,
      ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    this.renderer = renderer;
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.windowSize = windowSize;
    gameObjects = new ArrayList<>();
    createGameObjects();
  }

  private void createGameObjects() {
    var logo = new Logo(
        new LogoRenderer(resourceManager),
        new AbsoluteScenePositioner(new SceneCoordinate(0, 0, 0)),
        1.102292769,
        300,
        Logo.Type.TEAM);
    logo.setPosition(new RelativeScenePositioner(HorizontalPosition.CENTER,
        VerticalPosition.CENTER,
        0,
        0,
        windowSize,
        logo.getWidth(),
        logo.getHeight(),
        100));

    gameObjects.add(logo);
  }

  @Override
  public void makeActive() {

  }

  @Override
  public void initialize() {
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(gameObject -> gameObject.getRenderer().cleanup());
  }

  @Override
  public void updateState(float interval) {
    time += interval;
    if (time > 5 && !hasTransitioned) {
      eventBus.dispatch(new SceneTransitionEvent(new MainMenuScene(new MainMenuRenderer(
          resourceManager,
          eventBus,
          windowSize),
          resourceManager,
          eventBus,
          windowSize)));
      hasTransitioned = true;
    }
  }

  @Override
  public SceneRenderer getSceneRenderer() {
    return renderer;
  }
}
