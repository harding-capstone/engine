package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Button implements GameObject, Clickable, Hoverable {

  private final GameObjectRenderer<Button> renderer;
  @Setter
  private SceneCoordinate position;
  private final int width;
  private final int height;
  private final Runnable onClick;
  private boolean isClicked;
  private boolean isHovered;
  public State state = State.INACTIVE;

  public Button(GameObjectRenderer<Button> renderer, SceneCoordinate position, int width, int height, Runnable onClick) {
    this.renderer = renderer;
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
    var maxX = position.getX() + width;
    var minX = position.getX();
    var maxY = position.getY() + height;
    var minY = position.getY();

    return coordinate.getX() <= maxX
        && coordinate.getX() >= minX
        && coordinate.getY() <= maxY
        && coordinate.getY() >= minY;
  }

  public enum State {
    INACTIVE, HOVERED, CLICKED
  }
}
