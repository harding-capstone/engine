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
  private SortedMap<Integer, SortedMap<Integer, Float>> instances;

  public ParallaxBackground(GameObjectRenderer<ParallaxBackground> renderer, Type type) {
    this.renderer = renderer;
    this.position = new AbsoluteScenePosition(new SceneCoordinate(0, 0, 0));
    this.type = type;
    this.instances = new TreeMap<>();

    instances.put(1, new TreeMap<>());
    instances.put(2, new TreeMap<>());

    int numLayers;
    if (type == Type.PURPLE_MOUNTAINS) {
      numLayers = 5;
    } else {
      numLayers = 0;
    }

    for (int i = 1; i <= numLayers; i++) {
      instances.get(1).put(i, 0f);
    }

    for (int i = 1; i <= numLayers; i++) {
      instances.get(2).put(i, 1f);
    }
  }

  public void moveLayer(int instance, int layer, float newPosition) {
    if (newPosition < -1) {
      newPosition = 1;
    }
    if (newPosition > 1) {
      newPosition = -1;
    }
    instances.get(instance).put(layer, newPosition);
  }

  public float getLayerPosition(int instance, int layer) {
    return instances.get(instance).get(layer);
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
