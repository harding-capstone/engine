package com.shepherdjerred.capstone.engine.game.objects.text;

import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Text implements GameObject {

  private TextRenderer renderer;
  private final String text;
  private final FontName fontName;
  private final Color color;
  private final int size;
  @Setter
  private ScenePositioner position;

  public SceneObjectDimensions getSceneObjectDimensions() {
    return new SceneObjectDimensions(renderer.getWidth(), size);
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

  }
}
