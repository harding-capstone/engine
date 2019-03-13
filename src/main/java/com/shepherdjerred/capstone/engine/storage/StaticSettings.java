package com.shepherdjerred.capstone.engine.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StaticSettings implements Settings {
  private final String gameName;
  private final boolean isVsyncEnabled;
}
