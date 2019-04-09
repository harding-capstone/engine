package com.shepherdjerred.capstone.engine.engine.graphics.font;

import com.shepherdjerred.capstone.engine.engine.graphics.TextureQuad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FontCharacter {

  private final char character;
  private final float width;
  private final float height;
  private final TextureQuad textureCoordinates;

}
