package com.shepherdjerred.capstone.engine.settings;

public interface EngineSettings {

  int getWindowWidth();

  int getWindowHeight();

  boolean isVsyncEnabled();

  String getWindowTitle();

  boolean isWireframeEnabled();
}
