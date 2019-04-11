package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button.State;

public class ButtonRenderer implements GameObjectRenderer<Button> {

  private final ResourceManager resourceManager;
  private TexturedMesh normalMesh;
  private TexturedMesh hoveredMesh;
  private TexturedMesh clickedMesh;

  public ButtonRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  @Override
  public void init(Button sceneElement) throws Exception {
    var width = sceneElement.getWidth();
    var height = sceneElement.getHeight();

    var normalTexture = (Texture) resourceManager.get(TextureName.MAIN_MENU_BUTTON);
    var hoveredTexture = (Texture) resourceManager.get(TextureName.MAIN_MENU_BUTTON_HOVERED);
    var clickedTexture = (Texture) resourceManager.get(TextureName.MAIN_MENU_BUTTON_CLICKED);

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

    normalMesh = new TexturedMesh(mesh, normalTexture);
    hoveredMesh = new TexturedMesh(mesh, hoveredTexture);
    clickedMesh = new TexturedMesh(mesh, clickedTexture);
  }

  @Override
  public void render(Button sceneElement) {
    if (sceneElement.getState() == State.INACTIVE) {
      normalMesh.render();
    } else if (sceneElement.getState() == State.HOVERED) {
      hoveredMesh.render();
    } else if (sceneElement.getState() == State.CLICKED) {
      clickedMesh.render();
    }
  }

  @Override
  public void cleanup() {
  }
}
