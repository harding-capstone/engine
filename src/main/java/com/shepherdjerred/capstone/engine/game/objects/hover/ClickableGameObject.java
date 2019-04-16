package com.shepherdjerred.capstone.engine.game.objects.hover;

import com.shepherdjerred.capstone.engine.engine.collision.CollisionDetector;
import com.shepherdjerred.capstone.engine.engine.collision.GameObjectCollisionDetector;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;

public abstract class ClickableGameObject implements GameObject, Clickable, Hoverable {

  private final CollisionDetector collisionDetector;
  private final Runnable onClick;
  @Getter
  private boolean isClicked;
  @Getter
  private boolean isHovered;
  @Getter
  public State state = State.INACTIVE;

  public ClickableGameObject(WindowSize windowSize, Runnable onClick) {
    this.collisionDetector = new GameObjectCollisionDetector(this, windowSize);
    this.onClick = onClick;
  }

  @Override
  public void onClickBegin() {
    this.isClicked = true;
    updateState();
  }

  @Override
  public void onClickAbort() {
    this.isClicked = false;
    updateState();
  }

  @Override
  public void onClickEnd() {
    this.isClicked = false;
    onClick.run();
    updateState();
  }

  @Override
  public void onHover() {
    this.isHovered = true;
    updateState();
  }

  @Override
  public void onUnhover() {
    this.isHovered = false;
    updateState();
  }

  private void updateState() {
    if (isClicked) {
      state = State.CLICKED;
    } else if (isHovered) {
      state = State.HOVERED;
    } else {
      state = State.INACTIVE;
    }
  }

  @Override
  public boolean contains(SceneCoordinate coordinate) {
    return collisionDetector.hasCollision(coordinate);
  }

  public enum State {
    INACTIVE, HOVERED, CLICKED
  }
}
