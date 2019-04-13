package com.shepherdjerred.capstone.engine.engine.audio;

import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;

import com.shepherdjerred.capstone.engine.engine.events.audio.PlayAudioEvent;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

@Log4j2
@AllArgsConstructor
public class AudioPlayer {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;

  public void initialize() {
    String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    long device = alcOpenDevice(defaultDeviceName);

    int[] attributes = {0};
    long context = alcCreateContext(device, attributes);
    alcMakeContextCurrent(context);

    ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
    ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

    setupListener();
  }

  private void play(AudioName audioName) {

  }

  private void load() {

  }

  private void setupListener() {
    eventBus.registerHandler(PlayAudioEvent.class, new EventHandler<PlayAudioEvent>() {
      @Override
      public void handle(PlayAudioEvent playAudioEvent) {
        play(playAudioEvent.getAudioName());
      }
    });
  }
}
