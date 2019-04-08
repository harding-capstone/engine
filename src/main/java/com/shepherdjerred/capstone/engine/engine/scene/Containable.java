package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;

public interface Containable {

  boolean contains(SceneCoordinate sceneCoordinate);
}
