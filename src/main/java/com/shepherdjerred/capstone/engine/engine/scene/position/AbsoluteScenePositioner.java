package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AbsoluteScenePositioner implements ScenePositioner {

  private final SceneCoordinate sceneCoordinate;

  @Override
  public SceneCoordinate getSceneCoordinate(WindowSize windowSize,
      SceneObjectDimensions dimensions) {
    return sceneCoordinate;
  }
}
