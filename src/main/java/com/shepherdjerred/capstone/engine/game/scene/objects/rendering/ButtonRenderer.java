package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button;
import com.shepherdjerred.capstone.engine.game.scene.objects.Button.State;

public class ButtonRenderer implements ObjectRenderer<Button> {

  private TexturedMesh normalMesh;
  private TexturedMesh hoveredMesh;
  private TexturedMesh clickedMesh;
  private TextureLoader textureLoader;

  public ButtonRenderer(TextureLoader textureLoader) {
    this.textureLoader = textureLoader;
  }

  @Override
  public void init(Button sceneElement) {
    var width = sceneElement.getWidth();
    var height = sceneElement.getHeight();

    var normalTexture = textureLoader.loadTexture(TextureName.MAIN_MENU_BUTTON);
    var hoveredTexture = textureLoader.loadTexture(TextureName.MAIN_MENU_BUTTON_HOVERED);
    var clickedTexture = textureLoader.loadTexture(TextureName.MAIN_MENU_BUTTON_CLICKED);

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

    normalMesh = new TexturedMesh(vertices, textureCoordinates, indices, normalTexture);
    hoveredMesh = new TexturedMesh(vertices, textureCoordinates, indices, hoveredTexture);
    clickedMesh = new TexturedMesh(vertices, textureCoordinates, indices, clickedTexture);
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
    normalMesh.cleanup();
    hoveredMesh.cleanup();
    clickedMesh.cleanup();
  }
}
