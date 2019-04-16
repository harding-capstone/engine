package com.shepherdjerred.capstone.engine.engine.map.tileset;

import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.*;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.TERRAIN_TILESHEET;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps the names of tilesets to textures.
 */
public class TilesetNameToTextureMapper {

  private final Map<String, TextureName> textureMap;

  public TilesetNameToTextureMapper() {
    textureMap = new HashMap<>();
    initializeMap();
  }

  private void initializeMap() {
    textureMap.put("terrain", TERRAIN_TILESHEET);
    textureMap.put("desert", DESERT_TILESHEET);
    textureMap.put("castle", CASTLE_TILESHEET);
    textureMap.put("water", WATER_TILESHEET);
    textureMap.put("darkdimension", DARK_DIMENSION_TILESHEET);
  }

  public TextureName getTextureNameForTilesheet(String tilesheet) {
    return textureMap.get(tilesheet);
  }
}
