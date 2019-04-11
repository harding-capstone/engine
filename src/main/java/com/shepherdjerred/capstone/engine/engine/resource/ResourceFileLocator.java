package com.shepherdjerred.capstone.engine.engine.resource;

import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;

public interface ResourceFileLocator {

  /**
   * Returns the path to a texture file.
   */
  String getTexturePath(TextureName textureName);

  /**
   * Retuns the path to a font file.
   */
  String getFontPath(FontName fontName);
}
