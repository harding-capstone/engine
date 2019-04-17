package com.shepherdjerred.capstone.engine.engine.map.tileset;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheetCoordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString
@AllArgsConstructor
public class Tileset implements Comparable<Tileset> {

  private final String name;
  private final int firstTile;
  private final int columns;
  private final int rows;
  private final int tileSize;
  private final TextureName textureName;

  /**
   * This is brittle, but it'll work.
   */
  public TextureSheetCoordinates getTextureCoordinate(int tile) {
    var value = tile - firstTile;
    var column = value / rows;
    var row = value % rows;
    var width = rows * tileSize;
    var height = columns * tileSize;

//    log.info(String.format("v: %s, r: %s, c: %s", value, row, column));

    double minX = row * tileSize;
    double maxX = row * tileSize + tileSize;
    double minY = column * tileSize;
    double maxY = column * tileSize + tileSize;

    return new TextureSheetCoordinates(minX / width, maxX / width, minY / height, maxY / height);
  }

  @Override
  public int compareTo(Tileset tileset) {
    return this.firstTile - tileset.getFirstTile();
  }
}
