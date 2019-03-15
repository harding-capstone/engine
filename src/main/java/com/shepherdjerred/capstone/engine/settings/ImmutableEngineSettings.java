package com.shepherdjerred.capstone.engine.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImmutableEngineSettings implements EngineSettings {

  private final String windowTitle;
  private final int windowWidth;
  private final int windowHeight;
  private final boolean isVsyncEnabled;
  private final boolean isWireframeEnabled;
}
