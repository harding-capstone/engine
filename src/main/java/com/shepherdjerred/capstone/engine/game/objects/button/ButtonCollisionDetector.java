package com.shepherdjerred.capstone.engine.game.objects.button;

import com.shepherdjerred.capstone.engine.engine.collision.CollisionDetector;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public class ButtonCollisionDetector implements CollisionDetector {

  private final Button button;
  private final WindowSize windowSize;

  public ButtonCollisionDetector(Button button, WindowSize windowSize) {
    this.button = button;
    this.windowSize = windowSize;
  }

  @Override
  public boolean hasCollision(SceneCoordinate coordinate) {
    var position = button.getPosition()
        .getSceneCoordinate(windowSize, button.getSceneObjectDimensions());
    var maxX = position.getX() + button.getSceneObjectDimensions().getWidth();
    var minX = position.getX();
    var maxY = position.getY() + button.getSceneObjectDimensions().getHeight();
    var minY = position.getY();

    return coordinate.getX() <= maxX
        && coordinate.getX() >= minX
        && coordinate.getY() <= maxY
        && coordinate.getY() >= minY;
  }
}
