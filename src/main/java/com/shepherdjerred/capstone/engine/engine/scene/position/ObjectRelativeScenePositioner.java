package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ObjectRelativeScenePositioner implements ScenePositioner {

  private final GameObject anchor;
  private final int top;
  private final int bottom;
  private final int left;
  private final int right;
  private final int z;

  @Override
  public SceneCoordinate getSceneCoordinate(WindowSize windowSize,
      SceneObjectDimensions dimensions) {
    var x = getXCoordinate(windowSize, dimensions);
    var y = getYCoordinate(windowSize, dimensions);
    return new SceneCoordinate(x, y, z);
  }

  private float getXCoordinate(WindowSize windowSize, SceneObjectDimensions dimensions) {
    var orig = anchor.getPosition().getSceneCoordinate(windowSize, dimensions).getX();
    var anchorWidth = anchor.getSceneObjectDimensions().getWidth();
    return ((orig + (anchorWidth / 2)) - dimensions.getWidth() / 2) + right - left;
  }

  private float getYCoordinate(WindowSize windowSize, SceneObjectDimensions dimensions) {
    var orig = anchor.getPosition().getSceneCoordinate(windowSize, dimensions).getY();
    var anchorHeight = anchor.getSceneObjectDimensions().getHeight();
    return ((orig + (anchorHeight / 2)) - dimensions.getHeight() / 2) + top - bottom;
  }
}
