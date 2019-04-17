package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
    var coord = new SceneCoordinate(x, y, z);

    return coord;
  }

  private float getXCoordinate(WindowSize windowSize, SceneObjectDimensions dimensions) {
    var anchorPosition = anchor.getPosition().getSceneCoordinate(windowSize, dimensions).getX();
    var anchorWidth = anchor.getSceneObjectDimensions().getWidth();
    var objectWidth = dimensions.getWidth();

    var diff = Math.abs(anchorWidth - objectWidth);

    return (anchorPosition + diff / 2) - objectWidth + (right - left);
  }

  private float getYCoordinate(WindowSize windowSize, SceneObjectDimensions dimensions) {
    var anchorPosition = anchor.getPosition().getSceneCoordinate(windowSize, dimensions).getY();
    var anchorHeight = anchor.getSceneObjectDimensions().getHeight();
    var objectHeight = dimensions.getHeight();

    var diff = Math.abs(anchorHeight - objectHeight);

    return (anchorPosition + diff / 2) + (top - bottom);
  }
}
