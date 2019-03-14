package com.shepherdjerred.capstone.engine.engine.graphics;

import com.shepherdjerred.capstone.engine.engine.Coordinate;
import org.joml.Matrix4f;

public class Transformation {

  public Matrix4f getProjectionMatrix(float width, float height) {
    return new Matrix4f().ortho2D(0, width, height, 0);
  }

  // https://learnopengl.com/In-Practice/2D-Game/Rendering-Sprites
  public Matrix4f getModelMatrix(int width,
      int height,
      Coordinate offset,
      float rotate,
      float scale) {
    return new Matrix4f()
        .translate(offset.getX(), offset.getY(), 0)
        .translate(width * .5f, height * .5f, 0)
        .rotate((float) Math.toRadians(rotate), 0, 0, 1)
        .translate(width * -.5f, height * -.5f, 0)
        .scale(scale, scale, 0);
  }
}
