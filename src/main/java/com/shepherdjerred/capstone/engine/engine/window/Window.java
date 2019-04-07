package com.shepherdjerred.capstone.engine.engine.window;

public interface Window {

  void initialize();

  boolean shouldClose();

  void swapBuffers();

  void pollEvents();

  WindowSettings getWindowSettings();
}
