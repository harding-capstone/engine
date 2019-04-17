package com.shepherdjerred.capstone.engine.game.objects.checkbox;

import com.shepherdjerred.capstone.engine.engine.collision.GameObjectCollisionDetector;
import com.shepherdjerred.capstone.engine.engine.object.ClickableGameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Checkbox extends ClickableGameObject {

  private final GameObjectRenderer<Checkbox> renderer;

  @Setter
  private ScenePositioner position;
  private boolean isChecked;
  private final SceneObjectDimensions sceneObjectDimensions;

  public Checkbox(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner position,
      SceneObjectDimensions sceneObjectDimensions,
      Runnable onClick) {
    super(null, onClick);
    this.renderer = new CheckboxRenderer(resourceManager);
    this.position = position;
    this.sceneObjectDimensions = sceneObjectDimensions;
    setCollisionDetector(new GameObjectCollisionDetector(this, windowSize));
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

  @Override
  public void onClickBegin() {
    super.onClickBegin();
    this.isChecked = !this.isChecked;
  }
}
