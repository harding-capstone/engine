package com.shepherdjerred.capstone.engine.game.ui;

import com.shepherdjerred.capstone.engine.engine.graphics.Coordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Button {

  private Coordinate position;
  private TexturedMesh mesh;

  public Button(TextureLoader textureLoader, Coordinate position, int width, int height) {
    this.position = position;

    var texture = textureLoader.loadTexture(TextureName.TERRAIN);
    var textureSheet = new TextureSheet(texture, 16);

    var vertices = new float[] {
        0, 0, 0,
        0, height, 0,
        width, 0, 0,
        width, height, 0
    };

    var textureCoordinates = textureSheet.getCoordinatesForTexture(new Coordinate(2, 2))
        .asFloatArray();

    var indices = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    mesh = new TexturedMesh(vertices, textureCoordinates, indices, texture);
  }

  public void render() {
    mesh.render();
  }

  public void cleanup() {
    mesh.cleanup();
  }
}
