package com.shepherdjerred.capstone.engine.game.objects.map;

import com.shepherdjerred.capstone.engine.engine.map.MapLayers;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.Setter;

public class MapObject implements GameObject {

  private final ResourceManager resourceManager;
  private final MapRenderer mapRenderer;
  private final GameMapName gameMapName;
  @Getter
  private MapLayers mapLayers;
  @Getter
  @Setter
  private int xOffset;
  @Getter
  @Setter
  private int yOffset;


  public MapObject(ResourceManager resourceManager, GameMapName gameMapName) {
    this.resourceManager = resourceManager;
    this.gameMapName = gameMapName;
    this.mapRenderer = new MapRenderer(resourceManager);
  }

  @Override
  public void initialize() throws Exception {
    mapLayers = resourceManager.get(gameMapName);
    mapRenderer.initialize(this);
  }

  @Override
  public void cleanup() {
    mapRenderer.cleanup();
    resourceManager.free(gameMapName);
  }

  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions(0, 0);
  }

  @Override
  public ScenePositioner getPosition() {
    return new AbsoluteScenePositioner(new SceneCoordinate(0, 0, 0));
  }

  @Override
  public void setPosition(ScenePositioner scenePositioner) {
  }

  @Override
  public void render(WindowSize windowSize) {
    mapRenderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {
  }
}
