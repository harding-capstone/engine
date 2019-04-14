package com.shepherdjerred.capstone.engine.engine.collision;

import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;

public interface CollisionDetector {

  boolean hasCollision(SceneCoordinate sceneCoordinate);
}
