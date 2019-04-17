package com.shepherdjerred.capstone.engine.game.objects.textbutton;

import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Hoverable;
import com.shepherdjerred.capstone.engine.engine.scene.position.ObjectRelativeScenePositioner;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.button.Button;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import com.shepherdjerred.capstone.engine.game.objects.text.Text;
import com.shepherdjerred.capstone.engine.game.objects.text.TextRenderer;

public class TextButton implements GameObject, Clickable, Hoverable {

  private final Text text;
  private final Button button;

  public TextButton(ResourceManager resourceManager,
      WindowSize windowSize,
      ScenePositioner positioner,
      String text,
      FontName fontName,
      Color color,
      int fontSize,
      SceneObjectDimensions dimensions,
      Type type,
      Runnable onClick) {
    this.button = new Button(resourceManager,
        windowSize,
        positioner,
        dimensions,
        type,
        onClick);
    this.text = new Text(new TextRenderer(resourceManager),
        text,
        fontName,
        color,
        fontSize,
        new ObjectRelativeScenePositioner(button,
            0,
            0,
            0,
            0,
            (int) positioner.getSceneCoordinate(windowSize, dimensions).getZ() + 1));
  }

  @Override
  public void initialize() throws Exception {
    text.initialize();
    button.initialize();
  }

  @Override
  public void cleanup() {
    text.cleanup();
    button.cleanup();
  }

  @Override
  public SceneObjectDimensions getSceneObjectDimensions() {
    return button.getSceneObjectDimensions();
  }

  @Override
  public ScenePositioner getPosition() {
    return button.getPosition();
  }

  @Override
  public void setPosition(ScenePositioner scenePositioner) {
    button.setPosition(scenePositioner);
    text.setPosition(scenePositioner);
  }

  @Override
  public void render(WindowSize windowSize) {
    text.render(windowSize);
    button.render(windowSize);
  }

  @Override
  public void update(float interval) {

  }

  @Override
  public void onClickBegin() {
    button.onClickBegin();
  }

  @Override
  public void onClickAbort() {
    button.onClickAbort();
  }

  @Override
  public void onClickEnd() {
    button.onClickEnd();
  }

  @Override
  public boolean isClicked() {
    return button.isClicked();
  }

  @Override
  public void onHover() {
    button.onHover();
  }

  @Override
  public void onUnhover() {
    button.onUnhover();
  }

  @Override
  public boolean isHovered() {
    return button.isHovered();
  }

  @Override
  public boolean contains(SceneCoordinate sceneCoordinate) {
    return button.contains(sceneCoordinate);
  }
}
