package com.shepherdjerred.capstone.engine.game.objects.wizard;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Wizard implements GameObject {

  private final GameObjectRenderer<Wizard> renderer;
  @Getter
  private final Element element;
  @Getter
  @Setter
  private ScenePositioner position;
  @Setter
  private State state;
  private int frame;
  @Getter
  private final SceneObjectDimensions sceneObjectDimensions;

  public Wizard(ResourceManager resourceManager,
      ScenePositioner position,
      Element element,
      SceneObjectDimensions sceneObjectDimensions) {
    this.position = position;
    this.renderer = new WizardRenderer(resourceManager);
    this.element = element;
    this.state = State.STILL;
    this.frame = 0;
    this.sceneObjectDimensions = sceneObjectDimensions;
  }

  @Override
  public void initialize() throws Exception {
    renderer.initialize(this);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }

  @Override
  public void render(WindowSize windowSize) {
    renderer.render(windowSize, this);
  }

  @Override
  public void update(float interval) {
    frame += 1;
    int maxFrames;
    if (state == State.STILL) {
      maxFrames = 3;
    } else if (state == State.CASTING) {
      maxFrames = 7;
    } else {
      maxFrames = 3;
    }
    if (frame > maxFrames - 1) {
      frame = 0;
    }
  }

  public SpriteState getSpriteState() {
    return new SpriteState(state, frame);
  }

  public enum State {
    STILL, CASTING, WALKING_UP, WALKING_DOWN, WALKING_LEFT, WALKING_RIGHT
  }

  @Getter
  @ToString
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class SpriteState {

    private final State state;
    private final int frame;
  }
}