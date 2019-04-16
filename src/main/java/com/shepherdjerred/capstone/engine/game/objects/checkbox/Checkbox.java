package com.shepherdjerred.capstone.engine.game.objects.checkbox;

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
public class Checkbox extends ClickableGameObject {

  private final GameObjectRenderer<Checkbox> renderer;

  @Setter
  private ScenePositioner position;
  private final SceneObjectDimensions sceneObjectDimensions;

  public Checkbox(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner position,
      SceneObjectDimensions sceneObjectDimensions,
      Runnable onClick) {
    super(windowSize, onClick);
    this.renderer = new CheckboxRenderer(resourceManager);
    this.position = position;
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
}
