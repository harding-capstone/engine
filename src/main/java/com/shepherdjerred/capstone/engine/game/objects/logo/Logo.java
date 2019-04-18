package com.shepherdjerred.capstone.engine.game.objects.logo;

import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Logo implements GameObject {

  @Getter
  private boolean isInitialized;
  private final GameObjectRenderer<Logo> renderer;
  @Setter
  @Getter
  private ScenePositioner position;
  private final double aspectRatio;
  private final int height;
  @Getter
  private final Type type;

  public Logo(ResourceManager resourceManager,
      ScenePositioner position,
      double aspectRatio,
      int height,
      Type type) {
    this.renderer = new LogoRenderer(resourceManager);
    this.position = position;
    this.aspectRatio = aspectRatio;
    this.height = height;
    this.type = type;
  }

  @Override
  public void initialize() throws Exception {
    renderer.initialize(this);
    isInitialized = true;
  }

  @Override
  public void cleanup() {
    isInitialized = false;
    renderer.cleanup();
  }

  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions((int) (height * aspectRatio), height);
  }

  @Override
  public void render(WindowSize windowSize) {
    if (!isInitialized) {
      throw new IllegalStateException("Object not initialized");
    }
    renderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {

  }

  public enum Type {
    GAME, TEAM
  }
}
