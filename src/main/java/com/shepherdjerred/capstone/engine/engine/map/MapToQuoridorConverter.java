package com.shepherdjerred.capstone.engine.engine.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.logic.board.Coordinate;

public class MapToQuoridorConverter {

  private final BiMap<Coordinate, MapCoordinate> coordinates = HashBiMap.create();

  public MapToQuoridorConverter() {
    createMap();
  }

  private void createMap() {
    var baseX = 21;
    var baseY = 14;
    for (int x = 0; x <= 17; x++) {
      for (int y = 0; y < 17; y++) {
        coordinates.put(new Coordinate(x, y), new MapCoordinate(baseX + x, baseY + y));
      }
    }
  }

  public MapCoordinate convert(Coordinate boardCoordinate) {
    return coordinates.get(boardCoordinate);
  }

  public Coordinate convert(MapCoordinate coordinate) {
    return coordinates.inverse().get(coordinate);
  }
}
