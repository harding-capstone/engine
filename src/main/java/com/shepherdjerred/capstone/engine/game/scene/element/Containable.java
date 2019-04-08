package com.shepherdjerred.capstone.engine.game.scene.element;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;

public interface Containable {

  boolean contains(SceneCoordinate sceneCoordinate);
}
