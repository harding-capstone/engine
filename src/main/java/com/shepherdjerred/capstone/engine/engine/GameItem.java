package com.shepherdjerred.capstone.engine.engine;

import com.shepherdjerred.capstone.engine.engine.graphics.Mesh;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class GameItem {

  private final Mesh mesh;
  private float scale = 1;
  private float rotation = 0;
  private Coordinate position = new Coordinate(0, 0, 0);
}
