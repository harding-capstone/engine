package com.shepherdjerred.capstone.engine.engine.graphics.texture.locator;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;

public interface TextureFileLocator {

  /**
   * Returns the path to a texture file.
   */
  String getTexturePath(TextureName textureName);
}
