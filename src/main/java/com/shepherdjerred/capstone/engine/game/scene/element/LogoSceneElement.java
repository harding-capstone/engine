package com.shepherdjerred.capstone.engine.game.scene.element;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LogoSceneElement implements SceneElement {

  private final SceneCoordinate position;
  private final int width;
  private final int height;
  private final Type type;

  public enum Type {
    GAME, TEAM
  }
}
