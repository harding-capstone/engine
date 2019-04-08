package com.shepherdjerred.capstone.engine.game.map;

import java.util.SortedMap;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GameMap {

  private final TileDimension dimension;
  private final SortedMap<Integer, Layer> layerMap;

  public GameMap(TileDimension tileDimension) {
    this.dimension = tileDimension;
    layerMap = new TreeMap<>();
  }

  public void setLayer(Integer i, Layer layer) {
    layerMap.put(i, layer);
  }

  public Layer getLayer(Integer i) {
    return layerMap.get(i);
  }
}
