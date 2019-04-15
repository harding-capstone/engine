package com.shepherdjerred.capstone.engine.engine.map;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shepherdjerred.capstone.engine.engine.resource.ByteBufferLoader;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceLoader;
import java.nio.ByteBuffer;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class GameMapLoader implements ResourceLoader<GameMapName, GameMap> {

  private final ByteBufferLoader loader;
  private final ResourceFileLocator locator;

  @Override
  public GameMap get(GameMapName identifier) throws Exception {
    var tilesetNameMapper = new TilesetNameMapper();
    var filePath = locator.getMapPath(identifier);
    var mapByteBuffer = loader.load(filePath);
    var json = byteBufferToJson(mapByteBuffer);

    SortedSet<Tileset> tilesets = new TreeSet<>();

    var tilesetsAsArray = json.getAsJsonArray("tilesets");

    tilesetsAsArray.forEach(tilesetEntry -> {
      var tilesetAsObject = tilesetEntry.getAsJsonObject();

      var name = tilesetAsObject.getAsJsonPrimitive("name").getAsString();
      var firstId = tilesetAsObject.getAsJsonPrimitive("firstgid").getAsInt();
      var columns = tilesetAsObject.getAsJsonPrimitive("columns").getAsInt();
      var tileHeight = tilesetAsObject.getAsJsonPrimitive("tileheight").getAsInt();
      var tileWidth = tilesetAsObject.getAsJsonPrimitive("tilewidth").getAsInt();

      if (tileHeight != tileWidth) {
        throw new IllegalStateException();
      }

      var texture = tilesetNameMapper.getTextureNameForTilesheet(name);
      var tileset = new Tileset(name, firstId, columns, tileHeight, texture);
      tilesets.add(tileset);
    });

    var tilesetTileIdMapper = new TilesetTileIdMapper(tilesets);

    var layers = json.getAsJsonArray("layers");

    var mapWidth = json.getAsJsonPrimitive("width").getAsInt();
    var mapHeight = json.getAsJsonPrimitive("height").getAsInt();

    var dimensions = new TileDimension(mapWidth, mapHeight);
    var gameMap = new GameMap(dimensions);

    for (int layerId = 0; layerId < layers.size(); layerId++) {
      var layer = new Layer(dimensions);
      var layerTiles = layers.get(layerId)
          .getAsJsonObject()
          .getAsJsonArray("data");

      for (int tileId = 0; tileId < layerTiles.size(); tileId++) {
        var x = tileId / dimensions.getHeight();
        var y = tileId / dimensions.getWidth();
        var coord = new MapCoordinate(x, y);

        var tileset = tilesetTileIdMapper.getTilesetForTileId(tileId);

        layer.setTile(new MapCoordinate(x, y),
            new MapTile(coord, tileset.getTextureName(), tileset.getTextureCoordinate(tileId)));
      }

      gameMap.setLayer(layerId, layer);
    }

    return gameMap;
  }

  private JsonObject byteBufferToJson(ByteBuffer byteBuffer) {
    var parser = new JsonParser();
    var mapBytes = new byte[byteBuffer.remaining()];
    byteBuffer.get(mapBytes);
    var mapJsonString = new String(mapBytes, Charsets.UTF_8);
    return (JsonObject) parser.parse(mapJsonString);
  }
}
