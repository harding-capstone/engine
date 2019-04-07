package com.shepherdjerred.capstone.engine.game.scene.element;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ButtonSceneElement implements SceneElement, Clickable {

  private final SceneCoordinate position;
  private final int width;
  private final int height;
  private final Runnable onClick;

  @Override
  public void onClick() {
    onClick.run();
  }

  @Override
  public boolean contains(SceneCoordinate coordinate) {
    var maxX = position.getX() + width;
    var minX = 0;
    var maxY = position.getY() + height;
    var minY = 0;

    return coordinate.getX() <= maxX
        && coordinate.getX() >= minX
        && coordinate.getY() <= maxY
        && coordinate.getY() >= minY;
  }
}
