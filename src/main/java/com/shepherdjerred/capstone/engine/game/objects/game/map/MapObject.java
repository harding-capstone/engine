package com.shepherdjerred.capstone.engine.game.objects.game.map;

import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.map.MapLayers;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.SceneCoordinateOffset;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.Setter;

public class MapObject implements GameObject {

  @Getter
  private boolean isInitialized;
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

  public MapObject(ResourceManager resourceManager,
      GameMapName gameMapName) {
    this.resourceManager = resourceManager;
    this.gameMapName = gameMapName;
    this.mapRenderer = new MapRenderer(resourceManager);
    createFrame();
  }

  private void createFrame() {

  }

  @Override
  public void initialize() throws Exception {
    mapLayers = resourceManager.get(gameMapName);
    mapRenderer.initialize(this);
    isInitialized = true;
  }

  @Override
  public void cleanup() {
    isInitialized = false;
    mapRenderer.cleanup();
    resourceManager.free(gameMapName);
  }

  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions(0, 0);
  }

  @Override
  public ScenePositioner getPosition() {
    return new AbsoluteScenePositioner(new SceneCoordinate(0, 0, 0),
        new SceneCoordinateOffset(0, 0));
  }

  @Override
  public void setPosition(ScenePositioner scenePositioner) {
  }

  @Override
  public void render(WindowSize windowSize) {
    if (!isInitialized) {
      throw new IllegalStateException("Object not initialized");
    }
    mapRenderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {
  }
}
