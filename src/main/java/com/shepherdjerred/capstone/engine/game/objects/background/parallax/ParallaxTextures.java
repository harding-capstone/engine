package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import java.util.SortedMap;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ParallaxTextures {

  private final int numberOfLayers;
  private final SortedMap<Integer, TextureName> textures;

  public ParallaxTextures(int numberOfLayers) {
    this.numberOfLayers = numberOfLayers;
    textures = new TreeMap<>();
  }

  public void setTextures() {

  }

  public void setLayerTexture(int texture, TextureName textureName) {
    textures.put(texture, textureName);
  }
}
