package com.shepherdjerred.capstone.engine.engine.graphics.texture.locator;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import lombok.AllArgsConstructor;

/**
 * Returns the path to a texture file.
 */
@AllArgsConstructor
public class PathBasedTextureFileLocator implements TextureFileLocator {

  private final String basePath;

  @Override
  public String getTexturePath(TextureName textureName) {
    String fileName;
    switch (textureName) {
      case FROST_WALL:
        fileName = "wall_frost.png";
        break;
      case FIRE_WIZARD_FRONT:
        fileName = "front_fire.png";
        break;
      case GRASS:
        fileName = "grass.png";
        break;
      default:
        throw new UnsupportedOperationException(textureName.toString());
    }
    return basePath + fileName;
  }
}
