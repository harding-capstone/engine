package com.shepherdjerred.capstone.engine.engine.collision;

import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * Determines if there is a collision based on a ScenePositioner. Useful for game objects.
 */
@Setter
@AllArgsConstructor
public class ScenePositionCollisionDetector implements CollisionDetector {

  private ScenePositioner scenePositioner;
  private WindowSize windowSize;
  private int width;
  private int height;

  @Override
  public boolean hasCollision(SceneCoordinate coordinate) {
    var position = scenePositioner.getSceneCoordinate(windowSize, width, height);
    var maxX = position.getX() + width;
    var minX = position.getX();
    var maxY = position.getY() + height;
    var minY = position.getY();

    return coordinate.getX() <= maxX
        && coordinate.getX() >= minX
        && coordinate.getY() <= maxY
        && coordinate.getY() >= minY;
  }
}
