package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.collision.CollisionDetector;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import lombok.Getter;

public abstract class ClickableGameObject extends HoverableGameObject implements Clickable {

  private final Runnable onClick;
  private final Runnable onUnclick;
  @Getter
  private boolean isClicked;

  public ClickableGameObject(CollisionDetector collisionDetector,
      Runnable onHover,
      Runnable onUnhover,
      Runnable onClick,
      Runnable onUnclick) {
    super(collisionDetector, onHover, onUnhover);
    this.onClick = onClick;
    this.onUnclick = onUnclick;
  }

  public ClickableGameObject(CollisionDetector collisionDetector,
      Runnable onHover,
      Runnable onClick) {
    super(collisionDetector, onHover);
    this.onClick = onClick;
    this.onUnclick = null;
  }

  public ClickableGameObject(CollisionDetector collisionDetector, Runnable onClick) {
    super(collisionDetector);
    this.onClick = onClick;
    this.onUnclick = null;
  }

  @Override
  public void onClickBegin() {
    this.isClicked = true;
    if (onClick != null) {
      onClick.run();
    }
  }

  @Override
  public void onClickEnd() {
    this.isClicked = false;
    if (onUnclick != null) {
      onUnclick.run();
    }
  }

  @Override
  public void onClickAbort() {
    this.isClicked = false;
  }

  public State getState() {
    if (isClicked) {
      return State.CLICKED;
    } else if (isHovered()) {
      return State.HOVERED;
    } else {
      return State.INACTIVE;
    }
  }

  public enum State {
    INACTIVE, HOVERED, CLICKED
  }
}
