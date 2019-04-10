package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePosition;
import java.util.SortedMap;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A background spanning the entire window.
 */
@Getter
@ToString
public class ParallaxScrollingBackground implements GameObject {

  private final GameObjectRenderer<ParallaxScrollingBackground> renderer;
  @Setter
  private ScenePosition position;
  private final Type type;
  private SortedMap<Integer, SortedMap<Integer, ScenePosition>> positions;

  public ParallaxScrollingBackground(GameObjectRenderer<ParallaxScrollingBackground> renderer,
      ScenePosition scenePosition,
      Type type) {
    this.renderer = renderer;
    this.position = scenePosition;
    this.type = type;
    this.positions = new TreeMap<>();

    positions.put(1, new TreeMap<>());
    positions.put(2, new TreeMap<>());
    if (type == Type.PURPLE_MOUNTAINS) {
      positions.forEach((instance, layerMap) -> {

      });
    }
  }

  public enum Type {
    CEMETERY,
    CEMETERY_NIGHT,
    DESERT,
    DESERT_RED,
    GREEN_FOREST,
    GREEN_MOUNTAINS,
    NIGHT_FOREST,
    PLAINS,
    PURPLE_MOUNTAINS,
    RED_PLAINS
  }
}
