package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.Background;

public class BackgroundRenderer implements
    GameObjectRenderer<Background> {

  private TexturedMesh texturedMesh;
  private TextureLoader textureProvider;
  private WindowSize windowSize;

  public BackgroundRenderer(TextureLoader textureProvider, WindowSize windowSize) {
    this.textureProvider = textureProvider;
    this.windowSize = windowSize;
  }

  @Override
  public void init(Background gameObject) {
    var width = windowSize.getWidth();
    var height = windowSize.getHeight();

    Texture texture;
    var type = gameObject.getType();

    switch (type) {
      case PURPLE_MOUNTAINS:
        texture = textureProvider.get(TextureName.PURPLE_MOUNTAINS);
        break;
      default:
        throw new UnsupportedOperationException(gameObject.getType().toString());
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
    texturedMesh = new TexturedMesh(mesh, texture);
  }

  @Override
  public void render(WindowSize windowSize, Background sceneElement) {
    texturedMesh.render();
  }

  @Override
  public void cleanup() {
  }
}
