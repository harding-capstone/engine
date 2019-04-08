package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.engine.scene.GameObject;
import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Logo implements GameObject {

  private final GameObjectRenderer<Logo> renderer;
  @Setter
  private SceneCoordinate position;
  private final double aspectRatio;
  private final int height;
  private final Type type;

  public int getWidth() {
    return (int) (height * aspectRatio);
  }

  public enum Type {
    GAME, TEAM
  }
}
