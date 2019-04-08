package com.shepherdjerred.capstone.engine.game.map;

import java.util.HashMap;
import java.util.Map;

public class Layer {

  private final TileDimension dimension;
  private final Map<MapCoordinate, TileType> tiles;

  public Layer(TileDimension tileDimension) {
    this.dimension = tileDimension;
    this.tiles = new HashMap<>();
  }

  public void setTile(MapCoordinate coordinate, TileType tileType) {
    tiles.put(coordinate, tileType);
  }

  public TileType getTile(MapCoordinate coordinate) {
    return tiles.get(coordinate);
  }
}
