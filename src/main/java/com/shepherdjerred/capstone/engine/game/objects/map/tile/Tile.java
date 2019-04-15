package com.shepherdjerred.capstone.engine.game.objects.map.tile;

import com.shepherdjerred.capstone.engine.engine.map.MapTile;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.position.AbsoluteScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public class Tile implements GameObject {

  private final ResourceManager resourceManager;
  private final TileRenderer tileRenderer;
  private final MapTile mapTile;

  public Tile(ResourceManager resourceManager, MapTile mapTile) {
    this.resourceManager = resourceManager;
    this.tileRenderer = new TileRenderer();
    this.mapTile = mapTile;
  }

  @Override
  public void initialize() throws Exception {
    tileRenderer.initialize(this);
  }

  @Override
  public void cleanup() {
    tileRenderer.cleanup();
  }

  // TODO
  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions(16, 16);
  }

  @Override
  public ScenePositioner getPosition() {
    var m = mapTile.getCoordinate();
    return new AbsoluteScenePositioner(new SceneCoordinate(m.getX(), m.getY(), 1));
  }

  @Override
  public void setPosition(ScenePositioner scenePositioner) {
  }

  @Override
  public void render(WindowSize windowSize) {
    tileRenderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {

  }
}
