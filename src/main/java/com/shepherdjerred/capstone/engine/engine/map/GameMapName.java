package com.shepherdjerred.capstone.engine.engine.map;

import com.shepherdjerred.capstone.engine.engine.resource.ResourceIdentifier;
import java.util.Random;

public enum GameMapName implements ResourceIdentifier {
  GRASS, WINTER, DESERT;

  public static GameMapName random() {
    var random = new Random();
    var values = GameMapName.values();
    return values[random.nextInt(values.length)];
  }
}
