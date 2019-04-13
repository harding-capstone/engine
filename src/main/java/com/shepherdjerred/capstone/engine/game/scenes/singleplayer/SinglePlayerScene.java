package com.shepherdjerred.capstone.engine.game.scenes.singleplayer;

import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneAudio;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.HorizontalPosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.WindowRelativeScenePositioner.VerticalPosition;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.text.TextRenderer;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class SinglePlayerScene implements Scene {

  private final EventBus<Event> eventBus;
  private final ResourceManager resourceManager;
  @Getter
  private final SceneRenderer<SinglePlayerScene> sceneRenderer;
  @Getter
  private final List<GameObject> gameObjects;
  @Getter
  private final SceneAudio sceneAudio;

  public SinglePlayerScene(EventBus<Event> eventBus,
      ResourceManager resourceManager,
      SceneRenderer<SinglePlayerScene> sceneRenderer,
      SceneAudio sceneAudio) {
    this.eventBus = eventBus;
    this.resourceManager = resourceManager;
    this.sceneRenderer = sceneRenderer;
    this.gameObjects = new ArrayList<>();
    this.sceneAudio = sceneAudio;
  }

  @Override
  public void initialize() {
    var text = new Text(new TextRenderer(resourceManager),
        "Single Player",
        FontName.M5X7,
        Color.white(),
        12,
        new WindowRelativeScenePositioner(HorizontalPosition.CENTER,
            VerticalPosition.CENTER,
            0,
            0,
            0));

    gameObjects.add(text);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(gameObject -> gameObject.getRenderer().cleanup());
  }

  @Override
  public void updateState(float interval) {

  }
}
