package com.shepherdjerred.capstone.engine.engine.map;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Layer implements Comparable<Layer> {

  private final MapDimensions dimension;
  private final Map<MapCoordinate, MapTile> tiles;
  @Getter
  private final int z;

  public Layer(MapDimensions mapDimensions, int z) {
    this.dimension = mapDimensions;
    this.tiles = new HashMap<>();
    this.z = z;
  }

  public void setTile(MapCoordinate coordinate, MapTile mapTile) {
    if (!dimension.contains(coordinate)) {
      throw new IllegalArgumentException(coordinate + " is not a coordinate in this layer");
    }
    tiles.put(coordinate, mapTile);
  }

  public MapTile getTile(MapCoordinate coordinate) {
    return tiles.get(coordinate);
  }

  @Override
  public int compareTo(Layer layer) {
    return z - layer.getZ();
  }
}
