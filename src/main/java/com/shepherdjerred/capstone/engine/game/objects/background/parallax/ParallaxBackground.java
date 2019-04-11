package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
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
  private ScenePositioner position;
  private final Type type;
  private SortedMap<Integer, SortedMap<Integer, Float>> instances;

  public ParallaxBackground(GameObjectRenderer<ParallaxBackground> renderer, Type type) {
    this.renderer = renderer;
    this.position = new AbsoluteScenePositioner(new SceneCoordinate(0, 0, 0));
    this.type = type;
    this.instances = new TreeMap<>();

    instances.put(1, new TreeMap<>());
    instances.put(2, new TreeMap<>());

    int numLayers;
    if (type == Type.PURPLE_MOUNTAINS) {
      numLayers = 5;
    } else if (type == Type.PLAINS) {
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

  @Override
  public void update(float interval) {
    getInstances().forEach((instance, layers) -> {
      layers.forEach((layer, pos) -> {
        if (layer != 1) {
          moveLayer(instance, layer, (float) (pos - ((Math.pow(layer, 2)) * .00005)));
        }
      });
    });
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
