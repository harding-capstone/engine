package com.shepherdjerred.capstone.engine.game.objects.checkbox;

import com.shepherdjerred.capstone.engine.engine.collision.GameObjectCollisionDetector;
import com.shepherdjerred.capstone.engine.engine.object.ClickableAbstractGameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Checkbox extends ClickableAbstractGameObject {

  @Getter
  private boolean isChecked;

  public Checkbox(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner position,
      SceneObjectDimensions sceneObjectDimensions,
      Runnable onClick) {
    super(new CheckboxRenderer(resourceManager), sceneObjectDimensions, position, null, onClick);
    setCollisionDetector(new GameObjectCollisionDetector(this, windowSize));
  }

  @Override
  public void onClickBegin() {
    super.onClickBegin();
    this.isChecked = !this.isChecked;
  }
}
