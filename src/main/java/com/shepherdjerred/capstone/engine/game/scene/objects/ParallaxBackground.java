package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePosition;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePosition;
import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
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
public class ParallaxBackground implements GameObject {

  private final GameObjectRenderer<ParallaxBackground> renderer;
  @Setter
  private ScenePosition position;
  private final Type type;
  private SortedMap<Integer, SortedMap<Integer, ScenePosition>> positions;

  public ParallaxBackground(GameObjectRenderer<ParallaxBackground> renderer, Type type) {
    this.renderer = renderer;
    this.position = new AbsoluteScenePosition(new SceneCoordinate(0, 0, 0));
    this.type = type;
    this.positions = new TreeMap<>();

    positions.put(1, new TreeMap<>());
    positions.put(2, new TreeMap<>());

    var origin = new AbsoluteScenePosition(new SceneCoordinate(0, 0, 0));
    if (type == Type.PURPLE_MOUNTAINS) {
      positions.forEach((instance, layerMap) -> {
        layerMap.put(1, origin);
        layerMap.put(2, origin);
        layerMap.put(3, origin);
        layerMap.put(4, origin);
        layerMap.put(5, origin);
      });
    }
  }

  public void moveLayer(int instance, int layer, ScenePosition scenePosition) {
    positions.get(instance).put(layer, scenePosition);
  }

  public ScenePosition getLayerPosition(int instance, int layer) {
    return positions.get(instance).get(layer);
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
