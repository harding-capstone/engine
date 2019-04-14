package com.shepherdjerred.capstone.engine.engine.map;

import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

@ToString
public class Layer {

  private final TileDimension dimension;
  private final Map<MapCoordinate, Tile> tiles;

  public Layer(TileDimension tileDimension) {
    this.dimension = tileDimension;
    this.tiles = new HashMap<>();
  }

  public void setTile(MapCoordinate coordinate, Tile tile) {
    tiles.put(coordinate, tile);
  }

  public Tile getTile(MapCoordinate coordinate) {
    return tiles.get(coordinate);
  }
}
