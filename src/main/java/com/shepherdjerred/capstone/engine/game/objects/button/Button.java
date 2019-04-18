package com.shepherdjerred.capstone.engine.game.objects.button;

import com.shepherdjerred.capstone.engine.engine.collision.GameObjectCollisionDetector;
import com.shepherdjerred.capstone.engine.engine.object.ClickableAbstractGameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Button extends ClickableAbstractGameObject {

  @Getter
  private final Type type;

  public Button(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner position,
      SceneObjectDimensions sceneObjectDimensions,
      Type type,
      Runnable onClick) {
    super(new ButtonRenderer(resourceManager), sceneObjectDimensions, position, null, onClick);
    this.type = type;
    setCollisionDetector(new GameObjectCollisionDetector(this, windowSize));
  }

  public enum Type {
    HOME,
    GENERIC
  }
}
