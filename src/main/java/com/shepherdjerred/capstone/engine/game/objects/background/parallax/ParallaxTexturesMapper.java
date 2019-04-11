package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;

public class ParallaxTexturesMapper {

  public ParallaxTextures get(ParallaxBackground.Type type) {
    ParallaxTextures textures;

    switch (type) {
      case PURPLE_MOUNTAINS:
        textures = new ParallaxTextures(5);
        textures.setLayerTexture(1, TextureName.PURPLE_MOUNTAINS_A);
        textures.setLayerTexture(2, TextureName.PURPLE_MOUNTAINS_B);
        textures.setLayerTexture(3, TextureName.PURPLE_MOUNTAINS_C);
        textures.setLayerTexture(4, TextureName.PURPLE_MOUNTAINS_D);
        textures.setLayerTexture(5, TextureName.PURPLE_MOUNTAINS_E);
        break;
      case PLAINS:
        textures = new ParallaxTextures(5);
        textures.setLayerTexture(1, TextureName.PLAINS_A);
        textures.setLayerTexture(2, TextureName.PLAINS_B);
        textures.setLayerTexture(3, TextureName.PLAINS_C);
        textures.setLayerTexture(4, TextureName.PLAINS_D);
        textures.setLayerTexture(5, TextureName.PLAINS_E);
        break;
      default:
        throw new UnsupportedOperationException("Unknown type: " + type);
    }

    return textures;
  }
}
