package com.shepherdjerred.capstone.engine.engine.graphics;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class RenderedElement {

  private final TexturedMesh texturedMesh;
  private float scale = 1;
  private float rotation = 0;
  private RendererCoordinate position = new RendererCoordinate(0, 0, 0);
}