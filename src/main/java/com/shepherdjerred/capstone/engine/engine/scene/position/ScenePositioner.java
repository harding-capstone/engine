package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public interface ScenePositioner {

  SceneCoordinate getSceneCoordinate(WindowSize windowSize, SceneObjectDimensions sceneObjectDimensions);

}
