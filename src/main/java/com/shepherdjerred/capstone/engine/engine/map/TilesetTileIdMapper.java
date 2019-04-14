package com.shepherdjerred.capstone.engine.engine.map;

import java.util.SortedSet;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TilesetTileIdMapper {

  private final SortedSet<Tileset> tilesets;

  public Tileset getTilesetForTileId(int tileId) {
    Tileset lastMatch = tilesets.first();

    for (Tileset tileset : tilesets) {
      if (tileset.getFirstTile() > tileId) {
        break;
      } else {
        lastMatch = tileset;
      }
    }

    return lastMatch;
  }
}
