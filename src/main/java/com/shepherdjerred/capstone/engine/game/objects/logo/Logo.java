package com.shepherdjerred.capstone.engine.game.objects.logo;

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
public class Logo implements GameObject {

  private final GameObjectRenderer<Logo> renderer;
  @Setter
  private ScenePositioner position;
  private final double aspectRatio;
  private final int height;
  private final Type type;

  public int getWidth() {
    return (int) (height * aspectRatio);
  }

  @Override
  public void update(float interval) {

  }

  public enum Type {
    GAME, TEAM
  }
}
