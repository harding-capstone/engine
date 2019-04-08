package com.shepherdjerred.capstone.engine.game.scene.element.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.game.scene.element.ButtonSceneElement;
import com.shepherdjerred.capstone.engine.game.scene.element.ButtonSceneElement.State;

public class ButtonRenderer implements SceneElementRenderer<ButtonSceneElement> {

  private TexturedMesh mesh;
  private TextureLoader textureLoader;
  private Texture normal;
  private Texture hover;
  private Texture pressed;

  public ButtonRenderer(TextureLoader textureLoader) {
    this.textureLoader = textureLoader;
  }

  @Override
  public void init(ButtonSceneElement sceneElement) {
    var width = sceneElement.getWidth();
    var height = sceneElement.getHeight();

    normal = textureLoader.loadTexture(TextureName.MAIN_MENU_BUTTON);
    hover = textureLoader.loadTexture(TextureName.MAIN_MENU_BUTTON_HOVERED);
    pressed = textureLoader.loadTexture(TextureName.MAIN_MENU_BUTTON_CLICKED);

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

    mesh = new TexturedMesh(vertices, textureCoordinates, indices, normal);
  }

  @Override
  public void render(ButtonSceneElement sceneElement) {
    if (sceneElement.getState() == State.INACTIVE) {
      normal.bind();
    } else if (sceneElement.getState() == State.HOVERED) {
      hover.bind();
    } else if (sceneElement.getState() == State.CLICKED) {
      pressed.bind();
    }
    mesh.render();
  }

  @Override
  public void cleanup() {
    mesh.cleanup();
  }
}
