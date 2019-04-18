package com.shepherdjerred.capstone.engine.engine.scene;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public abstract class AbstractScene implements Scene {

  protected final ResourceManager resourceManager;
  protected final WindowSize windowSize;
  private final SceneRenderer sceneRenderer;
  @Getter
  protected final List<GameObject> gameObjects;

  public AbstractScene(ResourceManager resourceManager,
      WindowSize windowSize,
      SceneRenderer sceneRenderer) {
    this.resourceManager = resourceManager;
    this.windowSize = windowSize;
    this.sceneRenderer = sceneRenderer;
    gameObjects = new ArrayList<>();
  }

  @Override
  public void initialize() throws Exception {
    sceneRenderer.initialize(this);
    for (GameObject gameObject : gameObjects) {
      gameObject.initialize();
    }
  }

  @Override
  public void render(WindowSize windowSize) {
    sceneRenderer.render(this);
  }

  @Override
  public void cleanup() {
    gameObjects.forEach(GameObject::cleanup);
    sceneRenderer.cleanup();
  }

  @Override
  public void updateState(float interval) {
    gameObjects.forEach(gameObject -> gameObject.update(interval));
  }

}
