package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RelativeScenePosition implements ScenePosition {

  private final HorizontalPosition horizontalPosition;
  private final VerticalPosition verticalPosition;
  private final float horizontalOffset;
  private final float verticalOffset;
  private final WindowSize windowSize;
  private final float elementWidth;
  private final float elementHeight;

  @Override
  public SceneCoordinate getSceneCoordinate() {
    var x = getXCoordinate() + horizontalOffset;
    var y = getYCoordinate() + verticalOffset;
    return new SceneCoordinate(x, y, 0);
  }

  private float getXCoordinate() {
    if (horizontalPosition == HorizontalPosition.LEFT) {
      return 0;
    } else if (horizontalPosition == HorizontalPosition.RIGHT) {
      return windowSize.getWidth() - elementWidth;
    } else if (horizontalPosition == HorizontalPosition.CENTER) {
      return (windowSize.getWidth() - elementWidth) / 2;
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private float getYCoordinate() {
    if (verticalPosition == VerticalPosition.TOP) {
      return 0;
    } else if (verticalPosition == VerticalPosition.BOTTOM) {
      return windowSize.getHeight() - elementHeight;
    } else if (verticalPosition == VerticalPosition.CENTER) {
      return (windowSize.getHeight() - elementHeight) / 2;
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public enum HorizontalPosition {
    LEFT, RIGHT, CENTER
  }

  public enum VerticalPosition {
    TOP, BOTTOM, CENTER
  }
}
