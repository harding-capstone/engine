package com.shepherdjerred.capstone.engine.engine.map;

import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

@ToString
public class Layer {

  private final TileDimension dimension;
  private final Map<MapCoordinate, MapTile> tiles;

  public Layer(TileDimension tileDimension) {
    this.dimension = tileDimension;
    this.tiles = new HashMap<>();
  }

  public void setTile(MapCoordinate coordinate, MapTile mapTile) {
    tiles.put(coordinate, mapTile);
  }

  public MapTile getTile(MapCoordinate coordinate) {
    return tiles.get(coordinate);
  }
}
