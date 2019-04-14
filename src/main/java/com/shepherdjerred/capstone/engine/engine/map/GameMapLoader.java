package com.shepherdjerred.capstone.engine.engine.map;

import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shepherdjerred.capstone.engine.engine.resource.ByteBufferLoader;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceLoader;
import java.nio.ByteBuffer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class GameMapLoader implements ResourceLoader<GameMapName, GameMap> {

  private final ByteBufferLoader loader;
  private final ResourceFileLocator locator;

  @Override
  public GameMap get(GameMapName identifier) throws Exception {
    var filePath = locator.getMapPath(identifier);
    var mapByteBuffer = loader.load(filePath);
    var json = byteBufferToJson(mapByteBuffer);



    return null;
  }

  private JsonElement byteBufferToJson(ByteBuffer byteBuffer) {
    var parser = new JsonParser();
    var mapBytes = new byte[byteBuffer.remaining()];
    byteBuffer.get(mapBytes);
    var mapJsonString = new String(mapBytes, Charsets.UTF_8);
    return parser.parse(mapJsonString);
  }
}
