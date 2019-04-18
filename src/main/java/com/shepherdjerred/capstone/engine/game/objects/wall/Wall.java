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

  }

  @Override
  public void cleanup() {

  }

  @Override
  public void render(WindowSize windowSize) {

  }

  @Override
  public void update(float interval) {

  }

  public enum Type {
    LONE,
    ALL,
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_DOWN,
    UP_LEFT,
    UP_RIGHT,
    UP_LEFT_RIGHT,
    UP_DOWN_LEFT,
    UP_DOWN_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    DOWN_LEFT_RIGHT,
    LEFT_RIGHT,
  }
}
