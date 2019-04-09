package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
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
  private final Color color;
  @Setter
  private SceneCoordinate position;

}
