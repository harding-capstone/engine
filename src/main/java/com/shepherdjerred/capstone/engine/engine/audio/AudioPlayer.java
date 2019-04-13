package com.shepherdjerred.capstone.engine.engine.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;

import com.shepherdjerred.capstone.engine.engine.events.audio.PlayAudioEvent;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

@Log4j2
@RequiredArgsConstructor
public class AudioPlayer {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private long device;
  private long context;

  public void initialize() {
    String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    device = alcOpenDevice(defaultDeviceName);

    int[] attributes = {0};
    context = alcCreateContext(device, attributes);
    alcMakeContextCurrent(context);

    ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
    ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

    setupListener();
  }

  private void play(AudioName audioName) throws Exception {
    var audio = (Audio) resourceManager.get(audioName);
    int sourcePointer = alGenSources();
    alSourcei(sourcePointer, AL_BUFFER, audio.getAlBufferName());
    alSourcePlay(sourcePointer);
//    alDeleteSources(sourcePointer);
  }

  private void setupListener() {
    eventBus.registerHandler(PlayAudioEvent.class, playAudioEvent -> {
      try {
        log.info("Handling audio event");
        play(playAudioEvent.getAudioName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public void cleanup() {
    alcDestroyContext(context);
    alcCloseDevice(device);
  }
}
