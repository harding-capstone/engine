package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A background spanning the entire window.
 */
@Getter
@ToString
@AllArgsConstructor
public class Background implements GameObject {

  private final GameObjectRenderer<Background> renderer;
  @Setter
  private ScenePosition position;
  private final Type type;

  public enum Type {
    PURPLE_MOUNTAINS,
    RED_PLAINS
  }
}
