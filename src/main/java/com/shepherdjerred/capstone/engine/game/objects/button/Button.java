package com.shepherdjerred.capstone.engine.game.objects.button;

import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.hover.ClickableGameObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Button extends ClickableGameObject {

  private final GameObjectRenderer<Button> renderer;
  @Setter
  private ScenePositioner position;
  private final SceneObjectDimensions sceneObjectDimensions;
  @Getter
  private final Type type;

  public Button(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner position,
      SceneObjectDimensions sceneObjectDimensions,
      Type type,
      Runnable onClick) {
    super(windowSize, onClick);
    this.renderer = new ButtonRenderer(resourceManager);
    this.position = position;
    this.type = type;
    this.sceneObjectDimensions = sceneObjectDimensions;
  }

  @Override
  public void initialize() throws Exception {
    renderer.initialize(this);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {
  }

  public enum Type {
    GENERIC
  }
}
