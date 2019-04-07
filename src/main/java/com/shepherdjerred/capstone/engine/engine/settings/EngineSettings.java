package com.shepherdjerred.capstone.engine.engine.settings;

import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EngineSettings {

  private final String windowTitle;
  private final WindowSize windowSize;
  private final boolean isVsyncEnabled;
}
