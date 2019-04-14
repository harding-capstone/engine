package com.shepherdjerred.capstone.engine.game.objects.background;

import com.shepherdjerred.capstone.engine.engine.object.Dimensions;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A background spanning the entire window.
 */
@Getter
@ToString
@AllArgsConstructor
public class StaticBackground implements GameObject {

  private final GameObjectRenderer<StaticBackground> renderer;
  @Setter
  private ScenePositioner position;
  private final Type type;

  @Override
  public void initialize() throws Exception {
    renderer.initialize(this);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }

  @Override
  public Dimensions getDimensions() {
    // TODO?
    return new Dimensions(0, 0);
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {

  }

  public enum Type {
    PURPLE_MOUNTAINS,
    RED_PLAINS
  }
}
