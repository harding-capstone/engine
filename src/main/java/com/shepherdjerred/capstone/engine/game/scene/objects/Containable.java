package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;

public interface Containable {

  boolean contains(SceneCoordinate sceneCoordinate);
}
