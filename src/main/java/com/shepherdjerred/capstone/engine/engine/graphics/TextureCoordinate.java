package com.shepherdjerred.capstone.engine.engine.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A coordinate in a 2D texture.
 */
@Getter
@ToString
@AllArgsConstructor
public class TextureCoordinate {
  private final float x;
  private final float y;
}
