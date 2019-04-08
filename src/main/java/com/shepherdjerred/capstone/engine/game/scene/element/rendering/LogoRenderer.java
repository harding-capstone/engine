package com.shepherdjerred.capstone.engine.game.scene.element.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.game.scene.element.LogoSceneElement;
import com.shepherdjerred.capstone.engine.game.scene.element.LogoSceneElement.Type;

public class LogoRenderer implements
    SceneElementRenderer<LogoSceneElement> {

  private TexturedMesh mesh;
  private TextureLoader textureLoader;

  public LogoRenderer(TextureLoader textureLoader) {
    this.textureLoader = textureLoader;
  }

  @Override
  public void init(LogoSceneElement sceneElement) {
    var width = sceneElement.getWidth();
    var height = sceneElement.getHeight();

    Texture texture;
    if (sceneElement.getType() == Type.GAME) {
      texture = textureLoader.loadTexture(TextureName.GAME_LOGO);
    } else {
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

    mesh = new TexturedMesh(vertices, textureCoordinates, indices, texture);
  }

  @Override
  public void render(LogoSceneElement sceneElement) {
    mesh.render();
  }

  @Override
  public void cleanup() {
    mesh.cleanup();
  }
}
