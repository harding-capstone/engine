package com.shepherdjerred.capstone.engine.game.objects.button;

import com.shepherdjerred.capstone.engine.engine.collision.ButtonCollisionDetector;
import com.shepherdjerred.capstone.engine.engine.collision.CollisionDetector;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Button implements GameObject, Clickable, Hoverable {

  private final GameObjectRenderer<Button> renderer;
  private final CollisionDetector collisionDetector;
  @Setter
  private ScenePositioner position;
  private final int width;
  private final int height;
  private final Runnable onClick;
  private boolean isClicked;
  private boolean isHovered;
  public State state = State.INACTIVE;

  public Button(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner position,
      int width,
      int height,
      Runnable onClick) {
    this.renderer = new ButtonRenderer(resourceManager);
    this.collisionDetector = new ButtonCollisionDetector(this, windowSize);
    this.position = position;
    this.width = width;
    this.height = height;
    this.onClick = onClick;
  }

  @Override
  public void onClickBegin() {
    this.isClicked = true;
    onClick.run();
    updateState();
  }

  @Override
  public void onClickEnd() {
    this.isClicked = false;
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

  @Override
  public void update(float interval) {
  }

  public enum State {
    INACTIVE, HOVERED, CLICKED
  }
}
