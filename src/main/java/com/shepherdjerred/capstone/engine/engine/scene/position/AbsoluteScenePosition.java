package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AbsoluteScenePosition implements ScenePosition {

  private final SceneCoordinate sceneCoordinate;
}
