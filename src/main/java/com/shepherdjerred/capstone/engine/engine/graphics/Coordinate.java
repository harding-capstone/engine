package com.shepherdjerred.capstone.engine.engine.graphics;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Coordinate {

  private final float x;
  private final float y;
  private final float z;

  public Coordinate(float x, float y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }

  public Coordinate(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}
