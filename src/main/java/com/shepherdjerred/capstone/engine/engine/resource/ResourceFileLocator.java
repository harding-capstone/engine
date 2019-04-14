package com.shepherdjerred.capstone.engine.engine.resource;

import com.shepherdjerred.capstone.engine.engine.audio.AudioName;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;

public interface ResourceFileLocator {

  /**
   * Returns the path to a texture file.
   */
  String getTexturePath(TextureName textureName);

  /**
   * Returns the path to a font file.
   */
  String getFontPath(FontName fontName);

  String getAudioPath(AudioName audioName);

  String getMapPath(GameMapName mapName);
}
