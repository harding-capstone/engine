package com.shepherdjerred.capstone.engine.game.objects.background;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
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
  private ScenePositioner position;
  private final Type type;

  @Override
  public void update(float interval) {

  }

  public enum Type {
    PURPLE_MOUNTAINS,
    RED_PLAINS
  }
}
