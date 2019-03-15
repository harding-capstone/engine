package com.shepherdjerred.capstone.engine.engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

  // https://gamedev.stackexchange.com/questions/59161/is-opengl-appropriate-for-2d-games
  // https://en.wikibooks.org/wiki/OpenGL_Programming/Modern_OpenGL_Tutorial_2D
  public Matrix4f getProjectionMatrix(float width, float height) {
    return new Matrix4f().ortho2D(0, width, height, 0);
  }

  // TODO rotation should be done around the center of the object, not the origin
  // https://learnopengl.com/In-Practice/2D-Game/Rendering-Sprites
  // https://learnopengl.com/Getting-started/Transformations
  public Matrix4f getModelMatrix(Coordinate offset,
      float rotation,
      float scale) {
    var rotationTranslateVector = new Vector3f(scale * .5f, scale * .5f, 0);
    return new Matrix4f()
        .translate(offset.getX(), offset.getY(), 0)
        .translate(rotationTranslateVector)
        .rotate((float) Math.toRadians(rotation), 0, 0, 1)
        .translate(rotationTranslateVector.negate())
        .scale(scale, scale, 1);
  }
}
