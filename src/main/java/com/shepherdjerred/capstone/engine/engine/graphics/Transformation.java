package com.shepherdjerred.capstone.engine.engine.graphics;

import com.shepherdjerred.capstone.engine.engine.Coordinate;
import org.joml.Matrix4f;

public class Transformation {

  public Matrix4f getProjectionMatrix(float width, float height) {
    return new Matrix4f().ortho2D(0, width, height, 0);
  }

  public Matrix4f getModelMatrix(Coordinate offset, float rotate, float scale) {
    return new Matrix4f()
        .translate(offset.getX(), offset.getY(), 0)
        .rotate(rotate, offset.getX(), offset.getY(), 0)
        .scale(scale, scale, 0);
  }
}
