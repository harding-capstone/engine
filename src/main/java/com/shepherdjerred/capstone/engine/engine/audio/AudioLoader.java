package com.shepherdjerred.capstone.engine.engine.audio;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.libc.LibCStdlib.free;

import com.shepherdjerred.capstone.engine.engine.resource.ByteBufferLoader;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceLoader;
import java.nio.ShortBuffer;
import lombok.AllArgsConstructor;
import org.lwjgl.system.MemoryStack;

@AllArgsConstructor
public class AudioLoader implements ResourceLoader<AudioName, Audio> {

  private final ResourceFileLocator locator;
  private final ByteBufferLoader bufferLoader;

  @Override
  public Audio get(AudioName identifier) throws Exception {
    try (var stack = MemoryStack.stackPush()) {
      var channelsBuffer = stack.mallocInt(1);
      var sampleRateBuffer = stack.mallocInt(1);

      var file = locator.getAudioPath(identifier);
      var buffer = bufferLoader.load(file);
      ShortBuffer rawAudioBuffer = stb_vorbis_decode_memory(buffer,
          channelsBuffer,
          sampleRateBuffer);

      int channels = channelsBuffer.get();
      int sampleRate = sampleRateBuffer.get();

      int format = -1;
      if (channels == 1) {
        format = AL_FORMAT_MONO16;
      } else if (channels == 2) {
        format = AL_FORMAT_STEREO16;
      }

      int alBufferName = alGenBuffers();

      alBufferData(alBufferName, format, rawAudioBuffer, sampleRate);

      free(rawAudioBuffer);

      return new Audio(identifier,
          alBufferName,
          channels,
          sampleRate);
    }
  }
}
