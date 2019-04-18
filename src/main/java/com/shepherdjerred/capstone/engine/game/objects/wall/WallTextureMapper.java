package com.shepherdjerred.capstone.engine.game.objects.wall;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheetCoordinates;
import com.shepherdjerred.capstone.engine.game.objects.wall.Wall.Type;
import java.util.HashMap;
import java.util.Map;

public class WallTextureMapper {
  private final Map<Type, TextureSheetCoordinates> coordinatesMap;

  public WallTextureMapper() {
    this.coordinatesMap = new HashMap<>();
    initializeMap();
  }

  private void initializeMap() {
  }
}
