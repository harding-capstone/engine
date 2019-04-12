package com.shepherdjerred.capstone.engine.engine.audio;

import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

public class AudioPlayer {
  public void init() {
    String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    long device = alcOpenDevice(defaultDeviceName);

    int[] attributes = {0};
    long context = alcCreateContext(device, attributes);
    alcMakeContextCurrent(context);

    ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
    ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
  }
}
