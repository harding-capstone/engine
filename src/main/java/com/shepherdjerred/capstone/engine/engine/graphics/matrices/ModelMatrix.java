package com.shepherdjerred.capstone.engine.engine.graphics.matrices;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Getter
@ToString
@AllArgsConstructor
public class ModelMatrix {

  private final RendererCoordinate offset;
  private final float rotation;
  private final float scale;

  // TODO rotation should be done around the center of the object, not the origin
  // https://learnopengl.com/In-Practice/2D-Game/Rendering-Sprites
  // https://learnopengl.com/Getting-started/Transformations
  public Matrix4f getMatrix() {
    var rotationTranslateVector = new Vector3f(.5f * scale * 32, .5f * scale * 32, 0);
    return new Matrix4f()
        .translate(offset.getX(), offset.getY(), 0)
        .translate(rotationTranslateVector)
        .rotate((float) Math.toRadians(rotation), 0, 0, 1)
        .translate(rotationTranslateVector.negate())
        .scale(scale, scale, 1);
  }
}
