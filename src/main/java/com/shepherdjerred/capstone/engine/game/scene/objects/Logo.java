package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.ObjectRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Logo implements GameObject {

  private final ObjectRenderer<Logo> renderer;
  private final SceneCoordinate position;
  private final int width;
  private final int height;
  private final Type type;

  public enum Type {
    GAME, TEAM
  }
}
