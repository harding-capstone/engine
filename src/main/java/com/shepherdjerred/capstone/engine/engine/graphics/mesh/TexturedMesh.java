package com.shepherdjerred.capstone.engine.engine.graphics.mesh;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TexturedMesh {

  private final Mesh mesh;
  private final Texture texture;

  public void render() {
    texture.bind();
    mesh.render();
  }
}
