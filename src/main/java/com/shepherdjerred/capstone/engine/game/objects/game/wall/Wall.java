package com.shepherdjerred.capstone.engine.game.objects.game.wall;

import com.shepherdjerred.capstone.engine.engine.object.AbstractGameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;

public class Wall extends AbstractGameObject {

  public Wall(ResourceManager resourceManager,
      SceneObjectDimensions sceneObjectDimensions,
      ScenePositioner position) {
    super(new WallRenderer(resourceManager), sceneObjectDimensions, position);
  }
}
