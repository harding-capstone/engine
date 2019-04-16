package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.collision.CollisionDetector;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Collidable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import lombok.Getter;
import lombok.Setter;

public abstract class HoverableGameObject implements GameObject, Hoverable, Collidable {

  @Setter
  private CollisionDetector collisionDetector;
  private final Runnable onHover;
  private final Runnable onUnhover;
  @Getter
  private boolean isHovered;

  public HoverableGameObject(CollisionDetector collisionDetector,
      Runnable onHover,
      Runnable onUnhover) {
    this.collisionDetector = collisionDetector;
    this.onHover = onHover;
    this.onUnhover = onUnhover;
  }

  public HoverableGameObject(CollisionDetector collisionDetector, Runnable onHover) {
    this.collisionDetector = collisionDetector;
    this.onHover = onHover;
    this.onUnhover = null;
  }

  public HoverableGameObject(CollisionDetector collisionDetector) {
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
