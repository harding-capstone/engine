package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.Logo;
import com.shepherdjerred.capstone.engine.game.scene.objects.Logo.Type;

public class LogoRenderer implements
    GameObjectRenderer<Logo> {

  private final ResourceManager resourceManager;
  private TexturedMesh texturedMesh;

  public LogoRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  @Override
  public void init(Logo sceneElement) throws Exception {
    var width = sceneElement.getWidth();
    var height = sceneElement.getHeight();

    Texture texture;
    if (sceneElement.getType() == Type.GAME) {
      texture = resourceManager.get(TextureName.GAME_LOGO);
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

    var mesh = new Mesh(vertices, textureCoordinates, indices);
    texturedMesh = new TexturedMesh(mesh, texture);
  }

  @Override
  public void render(Logo sceneElement) {
    texturedMesh.render();
  }

  @Override
  public void cleanup() {
  }
}
