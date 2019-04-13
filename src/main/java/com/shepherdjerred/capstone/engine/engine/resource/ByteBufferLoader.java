package com.shepherdjerred.capstone.engine.engine.resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.BufferUtils;

@Log4j2
public class ByteBufferLoader {

  public ByteBuffer load(String filePath) throws IOException {
    log.info("Loading file from jar: " + filePath);

    try (var stream = getClass().getResourceAsStream(filePath)) {
      var bytes = stream.readAllBytes();
      var buffer = BufferUtils.createByteBuffer(bytes.length);

      log.info(bytes.length + " bytes or " + bytes.length / 1024 + "kb");

      buffer.put(bytes);
      buffer.flip();

      return buffer;
    }
  }
}
