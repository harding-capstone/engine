package com.shepherdjerred.capstone.engine.game.scene.element;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheet;

public class ButtonRenderer implements SceneElementRenderer<ButtonSceneElement> {

  private TexturedMesh mesh;
  private TextureLoader textureLoader;

  public ButtonRenderer(TextureLoader textureLoader) {
    this.textureLoader = textureLoader;
  }

  @Override
  public void init(ButtonSceneElement sceneElement) {
    var width = sceneElement.getWidth();
    var height = sceneElement.getHeight();

    var texture = textureLoader.loadTexture(TextureName.TERRAIN);
    var textureSheet = new TextureSheet(texture, 16);

    var vertices = new float[] {
        0, 0, 0,
        0, height, 0,
        width, 0, 0,
        width, height, 0
    };

    var textureCoordinates = textureSheet.getCoordinatesForTexture(new RendererCoordinate(2, 2))
        .asFloatArray();

    var indices = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    mesh = new TexturedMesh(vertices, textureCoordinates, indices, texture);
  }

  @Override
  public void render(ButtonSceneElement sceneElement) {
    mesh.render();
  }

  @Override
  public void cleanup() {
    mesh.cleanup();
  }
}
