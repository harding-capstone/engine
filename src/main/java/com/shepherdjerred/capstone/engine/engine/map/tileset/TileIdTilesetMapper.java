package com.shepherdjerred.capstone.engine.engine.map.tileset;

import java.util.SortedSet;
import lombok.AllArgsConstructor;

/**
 * Finds what tileset a tile ID belongs to based on a set of tilesets.
 */
@AllArgsConstructor
public class TileIdTilesetMapper {

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
