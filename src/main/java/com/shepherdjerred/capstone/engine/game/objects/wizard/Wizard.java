package com.shepherdjerred.capstone.engine.game.objects.wizard;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public class Wizard implements GameObject {

  private final GameObjectRenderer<Wizard> renderer;
  private final Element element;
  private final

  public Wizard(Element element) {

  }

  @Override
  public void initialize() throws Exception {

  }

  @Override
  public void cleanup() {

  }

  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return null;
  }

  @Override
  public ScenePositioner getPosition() {
    return null;
  }

  @Override
  public void setPosition(ScenePositioner scenePositioner) {

  }

  @Override
  public void render(WindowSize windowSize) {

  }

  @Override
  public void update(float interval) {

  }

  public enum State {
    CAST_
  }
}
