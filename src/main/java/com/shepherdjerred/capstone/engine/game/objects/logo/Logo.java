package com.shepherdjerred.capstone.engine.game.objects.logo;

import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Logo implements GameObject {

  private final GameObjectRenderer<Logo> renderer;
  @Setter
  @Getter
  private ScenePositioner position;
  private final double aspectRatio;
  private final int height;
  @Getter
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
  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions((int) (height * aspectRatio), height);
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {

  }

  public enum Type {
    GAME, TEAM
  }
}
