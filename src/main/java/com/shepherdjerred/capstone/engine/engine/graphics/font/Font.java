package com.shepherdjerred.capstone.engine.engine.graphics.font;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Font {

  private final int ascent;
  private final int descent;
  private final int gap;
  private final int glTextureName;
}
