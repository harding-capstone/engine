package com.shepherdjerred.capstone.engine.engine.map.tileset;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheetCoordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Tileset implements Comparable<Tileset> {

  private final String name;
  private final int firstTile;
  private final int columns;
  private final int tileSize;
  private final TextureName textureName;

  /**
   * This is brittle, but it'll work.
   */
  public TextureSheetCoordinates getTextureCoordinate(int tile) {
    var value = firstTile - tile;
    var column = value / columns;
    var row = value % columns;

    var minX = row * tileSize;
    var maxX = row * tileSize + tileSize;
    var minY = column * tileSize;
    var maxY = column * tileSize + tileSize;
    return new TextureSheetCoordinates(minX, maxX, minY, maxY);
  }

  @Override
  public int compareTo(Tileset tileset) {
    return this.firstTile - tileset.getFirstTile();
  }
}
