package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.collision.CollisionDetector;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Collidable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import lombok.Getter;
import lombok.Setter;

public abstract class HoverableAbstractGameObject extends AbstractGameObject implements Hoverable,
    Collidable {

  @Setter
  private CollisionDetector collisionDetector;
  private final Runnable onHover;
  private final Runnable onUnhover;
  @Getter
  private boolean isHovered;

  public HoverableAbstractGameObject(GameObjectRenderer renderer,
      SceneObjectDimensions objectDimensions,
      ScenePositioner positioner,
      CollisionDetector collisionDetector,
      Runnable onHover,
      Runnable onUnhover) {
    super(renderer, objectDimensions, positioner);
    this.collisionDetector = collisionDetector;
    this.onHover = onHover;
    this.onUnhover = onUnhover;
  }

  public HoverableAbstractGameObject(GameObjectRenderer renderer,
      SceneObjectDimensions objectDimensions,
      ScenePositioner positioner,
      CollisionDetector collisionDetector, Runnable onHover) {
    super(renderer, objectDimensions, positioner);
    this.collisionDetector = collisionDetector;
    this.onHover = onHover;
    this.onUnhover = null;
  }

  public HoverableAbstractGameObject(GameObjectRenderer renderer,
      SceneObjectDimensions objectDimensions,
      ScenePositioner positioner,
      CollisionDetector collisionDetector) {
    super(renderer, objectDimensions, positioner);
    this.collisionDetector = collisionDetector;
    this.onHover = null;
    this.onUnhover = null;
  }

  @Override
  public void onHover() {
    isHovered = true;
    if (onHover != null) {
      onHover.run();
    }
  }

  @Override
  public void onUnhover() {
    isHovered = false;
    if (onUnhover != null) {
      onUnhover.run();
    }
  }

  @Override
  public boolean contains(SceneCoordinate sceneCoordinate) {
    return collisionDetector.hasCollision(sceneCoordinate);
  }
}
