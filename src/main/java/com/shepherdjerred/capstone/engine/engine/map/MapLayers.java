package com.shepherdjerred.capstone.engine.engine.map;

import com.shepherdjerred.capstone.engine.engine.resource.Resource;
import java.util.SortedMap;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MapLayers implements Resource {

  private final MapDimensions dimension;
  private final SortedMap<Integer, Layer> layerMap;

  public MapLayers(MapDimensions mapDimensions) {
    this.dimension = mapDimensions;
    layerMap = new TreeMap<>();
  }

  public void setLayer(Integer i, Layer layer) {
    layerMap.put(i, layer);
  }

  public Layer getLayer(Integer i) {
    return layerMap.get(i);
  }

  @Override
  public void cleanup() {

  }
}
