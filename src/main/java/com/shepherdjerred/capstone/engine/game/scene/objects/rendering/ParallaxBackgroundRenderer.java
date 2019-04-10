package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.ParallaxBackground;
import java.util.SortedMap;
import java.util.TreeMap;

public class ParallaxBackgroundRenderer implements
    GameObjectRenderer<ParallaxBackground> {

  private Mesh mesh;
  private final SortedMap<Integer, Texture> textureMap;
  private TextureLoader textureLoader;
  private WindowSize windowSize;

  public ParallaxBackgroundRenderer(TextureLoader textureLoader, WindowSize windowSize) {
    this.textureLoader = textureLoader;
    this.windowSize = windowSize;
    this.textureMap = new TreeMap<>();
  }

  @Override
  public void init(ParallaxBackground sceneElement) {
    var width = windowSize.getWidth();
    var height = windowSize.getHeight();

    var type = sceneElement.getType();

    switch (type) {
      case PURPLE_MOUNTAINS:
        var pma = textureLoader.loadTexture(TextureName.PURPLE_MOUNTAINS_A);
        var pmb = textureLoader.loadTexture(TextureName.PURPLE_MOUNTAINS_B);
        var pmc = textureLoader.loadTexture(TextureName.PURPLE_MOUNTAINS_C);
        var pmd = textureLoader.loadTexture(TextureName.PURPLE_MOUNTAINS_D);
        var pme = textureLoader.loadTexture(TextureName.PURPLE_MOUNTAINS_E);

        textureMap.put(1, pma);
        textureMap.put(2, pmb);
        textureMap.put(3, pmc);
        textureMap.put(4, pmd);
        textureMap.put(5, pme);
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

    mesh = new Mesh(vertices, textureCoordinates, indices);
  }

  @Override
  public void render(ParallaxBackground sceneElement) {
    // TODO move the mesh.. somehow
    sceneElement.getPositions().forEach((instance, layers) -> {
      layers.forEach((layer, position) -> {
        var texture = textureMap.get(layer);
        texture.bind();
        mesh.render();
      });
    });
  }

  @Override
  public void cleanup() {
  }
}
