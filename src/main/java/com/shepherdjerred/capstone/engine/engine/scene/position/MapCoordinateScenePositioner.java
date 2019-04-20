package com.shepherdjerred.capstone.engine.engine.scene.position;

import com.shepherdjerred.capstone.engine.engine.map.MapCoordinate;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MapCoordinateScenePositioner implements ScenePositioner {

  @Getter
  @Setter
  private SceneCoordinateOffset offset;
  @Getter
  @Setter
  private MapCoordinate mapCoordinate;
  private final int z;

  @Override
  public SceneCoordinate getSceneCoordinate(WindowSize windowSize,
      SceneObjectDimensions sceneObjectDimensions) {
    var tileSize = Constants.RENDER_TILE_RESOLUTION;
    var x = mapCoordinate.getX() * tileSize;
    var y = mapCoordinate.getY() * tileSize;
    return new SceneCoordinate(x, y, z);
  }
}
