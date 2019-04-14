package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class WindowRelativeScenePositioner implements ScenePositioner {

  private final HorizontalPosition horizontalPosition;
  private final VerticalPosition verticalPosition;
  private final float horizontalOffset;
  private final float verticalOffset;
  private final float z;

  @Override
  public SceneCoordinate getSceneCoordinate(WindowSize windowSize,
      SceneObjectDimensions dimensions) {
    var x = getXCoordinate(windowSize, dimensions.getWidth()) + horizontalOffset;
    var y = getYCoordinate(windowSize, dimensions.getHeight()) + verticalOffset;
    return new SceneCoordinate(x, y, z);
  }

  private float getXCoordinate(WindowSize windowSize, int width) {
    if (horizontalPosition == HorizontalPosition.LEFT) {
      return 0;
    } else if (horizontalPosition == HorizontalPosition.RIGHT) {
      return windowSize.getWidth() - width;
    } else if (horizontalPosition == HorizontalPosition.CENTER) {
      return (windowSize.getWidth() - width) / 2;
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private float getYCoordinate(WindowSize windowSize, int height) {
    if (verticalPosition == VerticalPosition.TOP) {
      return 0;
    } else if (verticalPosition == VerticalPosition.BOTTOM) {
      return windowSize.getHeight() - height;
    } else if (verticalPosition == VerticalPosition.CENTER) {
      return (windowSize.getHeight() - height) / 2;
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
