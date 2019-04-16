package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import java.util.Random;
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
  private final ParallaxTexturesMapper texturesMapper;
  private final ParallaxBackgroundData parallaxBackgroundData;

  public ParallaxBackground(GameObjectRenderer<ParallaxBackground> renderer, Type type) {
    this.renderer = renderer;
    this.position = new AbsoluteScenePositioner(new SceneCoordinate(0, 0, 0));
    this.type = type;
    this.instances = new TreeMap<>();
    this.texturesMapper = new ParallaxTexturesMapper();

    instances.put(1, new TreeMap<>());
    instances.put(2, new TreeMap<>());

    parallaxBackgroundData = texturesMapper.get(type);
    var numLayers = parallaxBackgroundData.getNumberOfLayers();

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
  public void initialize() throws Exception {
    renderer.initialize(this);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }

  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions(0, 0);
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {
    var spread = 4;
    var minifier = .000001;
    getInstances().forEach((instance, layers) -> layers.forEach((layer, pos) -> {
      if (!parallaxBackgroundData.getLayerData(layer).isStatic()) {
        moveLayer(instance, layer, (float) (pos - ((Math.pow(layer, spread)) * minifier)));
      }
    }));
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
    RED_PLAINS;

    public Type random() {
      var random = new Random();
      var values = Type.values();
      return values[random.nextInt(values.length)];
    }
  }
}
