package com.shepherdjerred.capstone.engine.game.scene.element;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A background spanning the entire window.
 */
@Getter
@ToString
@AllArgsConstructor
public class BackgroundSceneElement implements SceneElement {

  private final SceneCoordinate position;
  private final Type type;

  @Override
  public SceneCoordinate getPosition() {
    return new SceneCoordinate(0, 0, 0);
  }

  @Override
  public boolean contains(SceneCoordinate coordinate) {
    return true;
  }

  public enum Type {
    PURPLE_MOUNTAINS
  }
}
