package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.ParallaxBackground;
import java.util.HashMap;
import java.util.Map;

public class ParallaxBackgroundRenderer implements
    GameObjectRenderer<ParallaxBackground> {

  private Mesh mesh;
  private final Map<Integer, Texture> textureMap;
  private TextureLoader textureLoader;
  private WindowSize windowSize;

  public ParallaxBackgroundRenderer(TextureLoader textureLoader, WindowSize windowSize) {
    this.textureLoader = textureLoader;
    this.windowSize = windowSize;
    this.textureMap = new HashMap<>();
  }

  @Override
  public void init(ParallaxBackground sceneElement) {
    var width = windowSize.getWidth();
    var height = windowSize.getHeight();

    var type = sceneElement.getType();

    switch (type) {
      case PURPLE_MOUNTAINS:
        break;
      default:
        throw new UnsupportedOperationException(sceneElement.getType().toString());
    }

    var vertices = new float[] {
        0, 0, 0,
        0, height, 0,
        width, 0, 0,
        width, height, 0
    };

    var textureCoordinates = new float[] {
        0, 0,
        0, 1,
        1, 0,
        1, 1
    };

    var indices = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    var mesh = new Mesh(vertices, textureCoordinates, indices);
  }

  @Override
  public void render(ParallaxBackground sceneElement) {

  }

  @Override
  public void cleanup() {
  }
}
