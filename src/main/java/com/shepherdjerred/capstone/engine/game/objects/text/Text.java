package com.shepherdjerred.capstone.engine.game.objects.text;

import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.scene.position.ScenePositioner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Text implements GameObject {

  private GameObjectRenderer<Text> renderer;
  private final String text;
  private final FontName fontName;
  private final Color color;
  private final int size;
  @Setter
  private ScenePositioner position;

  public int getHeight() {
    return size;
  }

  public int getWidth() {
    return size * text.length();
  }

  @Override
  public void update(float interval) {

  }
}
