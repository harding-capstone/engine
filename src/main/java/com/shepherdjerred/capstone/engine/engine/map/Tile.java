package com.shepherdjerred.capstone.engine.engine.map;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheetCoordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Tile {

  private final MapCoordinate coordinate;
  private final TextureName textureName;
  private final TextureSheetCoordinates coordinates;
}
