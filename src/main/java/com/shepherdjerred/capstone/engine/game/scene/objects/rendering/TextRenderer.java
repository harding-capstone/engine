package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

import com.shepherdjerred.capstone.engine.engine.graphics.font.Font;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.Text;

public class TextRenderer implements GameObjectRenderer<Text> {

  private final FontLoader fontLoader;
  private Font font;
  private Mesh mesh;

  public TextRenderer(FontLoader fontLoader) {
    this.fontLoader = fontLoader;
  }

  @Override
  public void init(Text sceneElement) {

    var vertices = new float[] {
        0, 0, 0,
        0, 300, 0,
        300, 0, 0,
        300, 300, 0
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

    try {
      font = fontLoader.load(FontName.M5X7);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void render(Text sceneElement) {
    glBindTexture(GL_TEXTURE_2D, font.getGlTextureName());
    mesh.render();
  }

  @Override
  public void cleanup() {

  }
}
