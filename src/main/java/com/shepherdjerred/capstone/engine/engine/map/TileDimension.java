package com.shepherdjerred.capstone.engine.engine.map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TileDimension {

  private final int width;
  private final int height;
}
