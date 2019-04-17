package com.shepherdjerred.capstone.engine.engine.map.tileset;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheetCoordinates;
import java.math.BigDecimal;
import java.math.MathContext;
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
    var column = value % columns;
    var row = value / columns;
    var width = columns * tileSize;
    var height = rows * tileSize;

    log.info(row);

    log.info(String.format("tileset: %s, tile value: %s || %s, row: %s, col: %s",
        name,
        value,
        tile,
        row,
        column));

    double minX = column * tileSize;
    double maxX = column * tileSize + tileSize;
    double minY = row * tileSize;
    double maxY = row * tileSize + tileSize;

//    log.info(String.format("width: %s, height %s", width, height));

    double scaledMinX = new BigDecimal(minX)
        .divide(BigDecimal.valueOf(width), MathContext.DECIMAL128)
        .floatValue();
    double scaledMaxX = new BigDecimal(maxX)
        .divide(BigDecimal.valueOf(width), MathContext.DECIMAL128)
        .floatValue();
    double scaledMinY = new BigDecimal(minY)
        .divide(BigDecimal.valueOf(height), MathContext.DECIMAL128)
        .floatValue();
    double scaledMaxY = new BigDecimal(maxY)
        .divide(BigDecimal.valueOf(height), MathContext.DECIMAL128)
        .floatValue();

//    log.info(String.format("minX %s maxX %s minY %s maxY %s", minX, maxX, minY, maxY));
//    log.info(String.format("SCALED minX %s maxX %s minY %s maxY %s",
//        scaledMinX,
//        scaledMaxX,
//        scaledMinY,
//        scaledMaxY));

    return new TextureSheetCoordinates(scaledMinX, scaledMaxX, scaledMinY, scaledMaxY);
  }

  @Override
  public int compareTo(Tileset tileset) {
    return this.firstTile - tileset.getFirstTile();
  }
}
