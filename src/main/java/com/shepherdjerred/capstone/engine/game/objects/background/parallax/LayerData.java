package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LayerData {

  private final TextureName textureName;
  private final boolean isStatic;
}
