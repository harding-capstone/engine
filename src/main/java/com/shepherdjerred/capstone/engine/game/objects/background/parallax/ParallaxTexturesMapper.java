package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;

public class ParallaxTexturesMapper {

  public ParallaxBackgroundData get(ParallaxBackground.Type type) {
    ParallaxBackgroundData textures;

    switch (type) {
      case PURPLE_MOUNTAINS:
        textures = new ParallaxBackgroundData(5);
        textures.setLayerData(1, new LayerData(TextureName.PURPLE_MOUNTAINS_A, true));
        textures.setLayerData(2, new LayerData(TextureName.PURPLE_MOUNTAINS_B, false));
        textures.setLayerData(3, new LayerData(TextureName.PURPLE_MOUNTAINS_C, false));
        textures.setLayerData(4, new LayerData(TextureName.PURPLE_MOUNTAINS_D, false));
        textures.setLayerData(5, new LayerData(TextureName.PURPLE_MOUNTAINS_E, false));
        break;
      case PLAINS:
        textures = new ParallaxBackgroundData(5);
        textures.setLayerData(1, new LayerData(TextureName.PLAINS_A, true));
        textures.setLayerData(2, new LayerData(TextureName.PLAINS_B, true));
        textures.setLayerData(3, new LayerData(TextureName.PLAINS_C, false));
        textures.setLayerData(4, new LayerData(TextureName.PLAINS_D, false));
        textures.setLayerData(5, new LayerData(TextureName.PLAINS_E, false));
        break;
      default:
        throw new UnsupportedOperationException("Unknown type: " + type);
    }

    return textures;
  }
}
