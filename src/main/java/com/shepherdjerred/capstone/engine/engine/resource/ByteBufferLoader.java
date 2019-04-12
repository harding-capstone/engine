package com.shepherdjerred.capstone.engine.engine.resource;

import static org.lwjgl.BufferUtils.createByteBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.BufferUtils;

@Log4j2
public class ByteBufferLoader {

  private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
    ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
    buffer.flip();
    newBuffer.put(buffer);
    return newBuffer;
  }

  /**
   * Reads the specified resource and returns the raw data as a ByteBuffer.
   *
   * @param resource the resource to read
   * @param bufferSize the initial buffer size
   * @return the resource data
   * @throws IOException if an IO error occurs
   */
  public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize)
      throws IOException {
    ByteBuffer buffer;

    Path path = Paths.get(resource);
    if (Files.isReadable(path)) {
      try (SeekableByteChannel fc = Files.newByteChannel(path)) {
        buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
        while (fc.read(buffer) != -1) {
          ;
        }
      }
    } else {
      try (
          InputStream source = ByteBufferLoader.class.getClassLoader().getResourceAsStream(resource);
          ReadableByteChannel rbc = Channels.newChannel(source)
      ) {
        buffer = createByteBuffer(bufferSize);

        while (true) {
          int bytes = rbc.read(buffer);
          if (bytes == -1) {
            break;
          }
          if (buffer.remaining() == 0) {
            buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
          }
        }
      }
    }

    buffer.flip();
    return buffer;
  }

  public ByteBuffer load(String filePath) throws IOException {
    log.info("Loading file from jar: " + filePath);

    try (var stream = getClass().getResourceAsStream(filePath)) {
      var bytes = stream.readAllBytes();
      var buffer = BufferUtils.createByteBuffer(bytes.length);

      log.info(bytes.length);

      buffer.put(bytes);
      buffer.flip();

      return buffer;
    }
  }
}
