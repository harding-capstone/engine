package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@AllArgsConstructor
public class ObjectRelativeScenePositioner implements ScenePositioner {

  private final GameObject anchor;
  @Getter
  @Setter
  private SceneCoordinateOffset offset;
  private final int z;

  @Override
  public SceneCoordinate getSceneCoordinate(WindowSize windowSize,
      SceneObjectDimensions dimensions) {
    var x = getXCoordinate(windowSize, dimensions);
    var y = getYCoordinate(windowSize, dimensions);

    return new SceneCoordinate(x, y, z);
  }

  private float getXCoordinate(WindowSize windowSize, SceneObjectDimensions dimensions) {
    var anchorPosition = anchor.getPosition().getSceneCoordinate(windowSize, dimensions).getX();
    var anchorWidth = anchor.getSceneObjectDimensions().getWidth();
    var objectWidth = dimensions.getWidth();

    var diff = Math.abs(anchorWidth - objectWidth);

    return (anchorPosition) + offset.getXOffset();
  }

  private float getYCoordinate(WindowSize windowSize, SceneObjectDimensions dimensions) {
    var anchorPosition = anchor.getPosition().getSceneCoordinate(windowSize, dimensions).getY();
    var anchorHeight = anchor.getSceneObjectDimensions().getHeight();
    var objectHeight = dimensions.getHeight();

    var diff = Math.abs(anchorHeight - objectHeight);

    return (anchorPosition)  + offset.getYOffset();
  }
}
