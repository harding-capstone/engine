package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
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
  public SceneCoordinate getSceneCoordinate(WindowSize windowSize, int width, int height) {
    var x = getXCoordinate(windowSize, width, height);
    var y = getYCoordinate(windowSize, width, height);
    return new SceneCoordinate(x, y, z);
  }

  private final float getXCoordinate(WindowSize windowSize, int width, int height) {
    var orig = anchor.getPosition().getSceneCoordinate(windowSize, width, height).getX();
    var anchorWidth = anchor.getDimensions().getWidth();
    return orig + (anchorWidth / 2);
  }

  private final float getYCoordinate(WindowSize windowSize, int width, int height) {
    var orig = anchor.getPosition().getSceneCoordinate(windowSize, width, height).getY();
    var anchorHeight = anchor.getDimensions().getHeight();
    return orig + (anchorHeight / 2);
  }
}
