package com.shepherdjerred.capstone.engine.game.objects.wall;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.Getter;
import lombok.Setter;

public class Wall implements GameObject {

  @Getter
  private boolean isInitialized;
  @Getter
  private final SceneObjectDimensions sceneObjectDimensions;
  @Getter
  @Setter
  private ScenePositioner position;
  private final Element element;
  private final Type type;

  public Wall(SceneObjectDimensions dimensions,
      ScenePositioner positioner,
      Element element,
      Type type) {
    this.sceneObjectDimensions = dimensions;
    this.position = positioner;
    this.element = element;
    this.type = type;
  }

  @Override
  public void initialize() throws Exception {
    isInitialized = true;
  }

  @Override
  public void cleanup() {
    isInitialized = false;
  }

  @Override
  public void render(WindowSize windowSize) {
    if (!isInitialized) {
      throw new IllegalStateException("Object not initialized");
    }
  }

  @Override
  public void update(float interval) {

  }

  public enum Type {
    HORIZONTAL,
    VERTICAL,
    CENTER,
    HORIZONTAL_LEFT,
    HORIZONTAL_RIGHT,
    HORIZONTAL_LEFT_RIGHT,
    VERTICAL_UP,
    VERTICAL_DOWN,
    VERTICAL_UP_DOWN,
    CENTER_UP,
    CENTER_DOWN,
    CENTER_LEFT,
    CENTER_RIGHT,
    CENTER_UP_DOWN,
    CENTER_LEFT_RIGHT,
    CENTER_UP_LEFT,
    CENTER_UP_RIGHT,
    CENTER_DOWN_LEFT,
    CENTER_DOWN_RIGHT
  }
}
