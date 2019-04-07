package com.shepherdjerred.capstone.engine.game.scene.element;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;

public interface SceneElement {

  SceneCoordinate getPosition();

  /**
   * Determines if a coordinate is contained within the element.
   */
  boolean contains(SceneCoordinate coordinate);
}
